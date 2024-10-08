package ru.tutorialclient.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.tutorialclient.modules.settings.imp.ModeSetting;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.BloomHelper;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;

import java.awt.*;

public class ModeObject extends Object {

    public ModeSetting set;
    public ModuleObject object;

    public ModeObject(ModuleObject object, ModeSetting set) {
        this.object = object;
        this.set = set;
        setting = set;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        int offset = 0;
        float offsetY = 0;
        int lines = 1;
        float size = 0;
        for (String mode : set.modes) {

            float preOffset = size + Fonts.msBold[11].getWidth(mode) + 3;
            if (preOffset > width - 20) {
                break;
            }
            size += Fonts.msBold[11].getWidth(mode) + 3;
        }

        for (String mode : set.modes) {
            float preOffset = offset + Fonts.msBold[11].getWidth(mode) + 3;
            if (preOffset > size) {
                lines++;
                offset = 0;
            }
            offset += Fonts.msBold[11].getWidth(mode) + 3;
        }

        height += 8;
        Fonts.msBold[12].drawString(stack, set.getName(), x + 10, y + height / 2f - 8, ColorUtil.rgba(161, 164, 177, 255));

        RenderUtil.Render2D.drawRoundedCorner(x + 10, y + 9, size + 7, 11 * lines, 3, ColorUtil.rgba(11, 12, 15, 255));
        height += 11 * (lines - 1);
        offset = 0;
        offsetY = 0;
        int i = 0;
        for (String mode : set.modes) {

            float preOff = offset + Fonts.msBold[11].getWidth(mode) + 3;
            if (preOff > size) {
                offset = 0;
                offsetY += 11;
            }
            if (set.getIndex() == i) {
                int finalOffset = offset;
                float finalOffsetY = offsetY;
                BloomHelper.registerRenderCall(() -> {
                    Fonts.msBold[11].drawString(stack, mode, x + 15 + finalOffset, y + 14f + finalOffsetY, ColorUtil.rgba(119, 121, 134, 255));
                });
                Fonts.msBold[11].drawString(stack, mode, x + 15 + offset, y + 14f + offsetY, Color.WHITE.getRGB());
            } else
                Fonts.msBold[11].drawString(stack, mode, x + 15 + offset, y + 14f + offsetY, ColorUtil.rgba(26, 30, 41, 255));
            offset += Fonts.msBold[11].getWidth(mode) + 3;
            i++;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float offset = 0;
        float offsetY = 0;
        int i = 0;
        float size = 0;
        for (String mode : set.modes) {

            float preOffset = size + Fonts.msBold[11].getWidth(mode) + 3;
            if (preOffset > width - 20) {
                break;
            }
            size += Fonts.msBold[11].getWidth(mode) + 3;
        }

        for (String mode : set.modes) {
            float preOff = offset + Fonts.msBold[11].getWidth(mode) + 3;
            if (preOff > size) {
                offset = 0;
                offsetY += 11;
            }
            if (RenderUtil.isInRegion(mouseX, mouseY, x + 15 + offset, y + 12f + offsetY, Fonts.msBold[11].getWidth(mode), Fonts.msBold[11].getFontHeight() / 2f + 3)) {
                set.setIndex(i);
            }

            offset += Fonts.msBold[11].getWidth(mode) + 3;
            i++;
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
