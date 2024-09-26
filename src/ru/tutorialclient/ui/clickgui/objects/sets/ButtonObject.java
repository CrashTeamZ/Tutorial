package ru.tutorialclient.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.tutorialclient.modules.settings.imp.ButtonSetting;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;

import java.awt.*;

public class ButtonObject extends Object {

    public ButtonSetting set;
    public ModuleObject object;




    public ButtonObject(ModuleObject object, ButtonSetting set) {
        this.object = object;
        this.set = set;
        setting = set;

    }
    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        Fonts.msBold[13].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 2, Color.WHITE.getRGB());

        float wwidth = Math.max(10, Fonts.msBold[13].getWidth("Открыть") + 4);
        RenderUtil.Render2D.drawRoundedCorner(x + width - wwidth - 10,y + 2, wwidth,10, 2.5f,ColorUtil.rgba(20, 21, 24, 255));

        Fonts.msBold[13].drawCenteredString(stack, "Открыть", x + width - wwidth - 10  + wwidth / 2f, y + height / 2f - Fonts.msBold[13].getFontHeight() / 2f + 2, Color.WHITE.getRGB());

    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX,mouseY)) {
            float wwidth = Math.max(10, Fonts.msBold[13].getWidth("Открыть") + 4);
            if (RenderUtil.isInRegion(mouseX,mouseY,x + width - wwidth - 10,y + 2, wwidth,10)) {
                set.getRun().run();
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
