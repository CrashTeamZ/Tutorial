package ru.tutorialclient.modules.impl.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import org.joml.Vector4d;
import org.joml.Vector4i;
import org.lwjgl.opengl.GL11;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.render.EventRender;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.MultiBoxSetting;
import ru.tutorialclient.ui.midnight.Style;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.glu.GLU;
import ru.tutorialclient.util.math.MathUtil;
import ru.tutorialclient.util.math.PlayerPositionTracker;
import ru.tutorialclient.util.render.RenderUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static ru.tutorialclient.util.math.PlayerPositionTracker.isInView;
import static ru.tutorialclient.util.render.ColorUtil.rgba;
import static ru.tutorialclient.util.render.RenderUtil.Render2D.*;
import static ru.tutorialclient.util.render.RenderUtil.Render2D.drawMCVertical;

/**
 * @author dedinside
 * @since 26.06.2023
 */
@FunctionAnnotation(name = "ItemESP", type = Type.Render)
public class ItemESP extends Function {

    public MultiBoxSetting elements = new MultiBoxSetting("�������� ���������",
            new BooleanOption("�����", false),
            new BooleanOption("�����", true));

    public ItemESP() {
        addSettings(elements);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender render) {
            if (render.isRender3D()) {
                updateItemsPositions(render.partialTicks);
            }

            if (render.isRender2D()) {
                renderItemElements(render.matrixStack);
            }
        }
    }

    public HashMap<Vector4d, ItemEntity> positions = new HashMap<>();

    private void updateItemsPositions(float partialTicks) {
        positions.clear();
        for (Entity entity : mc.world.getAllEntities()) {
            if (isInView(entity) && entity instanceof ItemEntity item) {
                Vector4d position = PlayerPositionTracker.updatePlayerPositions(item, partialTicks);
                if (position != null) {
                    positions.put(position, item);
                }
            }
        }
    }

    private void renderItemElements(MatrixStack stack) {
        GL11.glColor4f(1, 1, 1, 1);
        int main = rgba(255, 255, 255, 255);
        int back = rgba(0, 0, 0, 128);
        Style current = Managment.STYLE_MANAGER.getCurrentStyle();
        Vector4i colors = new Vector4i(
                current.getColor(0),
                current.getColor(90),
                current.getColor(180),
                current.getColor(270)
        );

        for (Map.Entry<Vector4d, ItemEntity> entry : positions.entrySet()) {
            Vector4d position = entry.getKey();
            ItemEntity item = entry.getValue();

            if (position != null) {
                double x = position.x;
                double y = position.y;
                double endX = position.z;
                double endY = position.w;

                double widthPos = endX - x;
                double heightPos = endY - y;

                if (elements.get(0)) {
                    // ��������� ���� ��������������
                    drawMcRect(x - 0.5F, y - 0.5F, endX + 1, y + 1, back);
                    drawMcRect(x - 0.5F, endY - 0.5F, endX + 1, endY + 1, back);
                    drawMcRect(x - 0.5F, y + 1, x + 1, endY - 0.5F, back);
                    drawMcRect(endX - 0.5F, y + 1, endX + 1, endY - 0.5F, back);

                    float size = 1;

                    drawMCHorizontal(x, y, endX, y + 0.5F * size, colors.y, colors.x);
                    drawMCHorizontal(x, endY, endX, endY + 0.5F * size, colors.w, colors.z);
                    drawMCVertical(x, y, x + 0.5F * size, endY + 0.5F * size, colors.w, colors.y);
                    drawMCVertical(endX, y, endX + 0.5F * size, endY + 0.5F * size, colors.z, colors.x);

                }
                if (elements.get(1)) {
                        String tag = (item.getItem().getDisplayName().getString() +
                                (item.getItem().getCount() < 1 ? "" : " x" + item.getItem().getCount()));
                        Fonts.msMedium[10].drawStringWithShadow(stack, tag, (x + ((endX - x) / 2) - Fonts.msMedium[10].getWidth(tag) / 2),
                                y - 5, -1);

                }
            }
        }
    }
}
