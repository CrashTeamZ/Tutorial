package ru.tutorialclient.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;
import ru.tutorialclient.util.render.animation.AnimationMath;

import java.awt.*;

public class BooleanObject extends Object {

    public ModuleObject object;
    public BooleanOption set;
    public float enabledAnimation;

    public BooleanObject(ModuleObject object, BooleanOption set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        y-=1;
        double max = !set.get() ? 0 : 6.5f;
        this.enabledAnimation = AnimationMath.fast(enabledAnimation, (float) max, 10);

        Fonts.msBold[13].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 2, Color.WHITE.getRGB());
        RenderUtil.Render2D.drawRoundedCorner(x + width - 23.5f,y + 5,14.5f, 9, 1,ColorUtil.rgba(20, 21, 24, 255));
        int color = ColorUtil.interpolateColor(ColorUtil.rgba(42, 56, 73, 255), ColorUtil.rgba(255, 255, 255, 255), enabledAnimation / 6.5f);
        RenderUtil.Render2D.drawShadow(x + width - 23 + 3 + enabledAnimation - 2.5f,y + 8 - 2.5f,5, 5, 15, color);
        RenderUtil.Render2D.drawRoundedCorner(x + width - 23 + 3 + enabledAnimation - 3 + 1,y + 6,5,7,1, color);

//        Style current = Managment.STYLE_MANAGER.getCurrentStyle();
//        Vector4i colors = new Vector4i(
//                current.getColor(0),
//                current.getColor(90),
//                current.getColor(180),
//                current.getColor(270)
//        );
//        drawRoundedRect(x + width - 22, y + 4, 17, 9, 4, reAlphaInt(light, 100));
//        drawShadow(x + width - 17 + enabledAnimation - 4, y + 3 + 5.5F - 4, 8, 8, 10,reAlphaInt(ColorUtil.interpolateColor(Color.BLACK.getRGB(), colors.x, enabledAnimation / 7f), 100));
//        drawRoundCircle(x + width - 17 + enabledAnimation, y + 3 + 5.5F, 5,
//                reAlphaInt(ColorUtil.interpolateColor(light, colors.x, enabledAnimation / 7f), 100),
//                reAlphaInt(ColorUtil.interpolateColor(light, colors.y, enabledAnimation / 7f), 100),
//                reAlphaInt(ColorUtil.interpolateColor(light, colors.z, enabledAnimation / 7f), 100),
//                reAlphaInt(ColorUtil.interpolateColor(light, colors.w, enabledAnimation / 7f), 100));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isHovered(mouseX, mouseY)) {
                set.toggle();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }


}
