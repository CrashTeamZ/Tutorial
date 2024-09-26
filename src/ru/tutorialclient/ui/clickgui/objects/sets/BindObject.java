package ru.tutorialclient.ui.clickgui.objects.sets;

import com.mojang.blaze3d.matrix.MatrixStack;
import ru.tutorialclient.modules.settings.imp.BindSetting;
import ru.tutorialclient.ui.clickgui.binds.BindWindow;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;

import java.util.concurrent.ThreadLocalRandom;

public class BindObject extends Object {

    public BindSetting set;
    public ModuleObject object;

    public BindWindow bindWindow;
    public boolean isBinding;


    public BindObject(ModuleObject object, BindSetting set) {
        this.object = object;
        this.set = set;
        setting = set;

        bindWindow = new BindWindow(this);
        bindWindow.x = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.y = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.width = 178 / 2f;
        bindWindow.height = 73 / 2f;
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        Fonts.msLight[13].drawString(stack, set.getName(), x + 10, y + height / 2f - Fonts.msLight[13].getFontHeight() / 2f + 2, ColorUtil.rgba(161, 166, 179, 255));

        float wwidth = Math.max(10, Fonts.msLight[13].getWidth(ClientUtil.getKey(set.getKey()) == null ? "" : ClientUtil.getKey(set.getKey()).toUpperCase()) + 4);
        RenderUtil.Render2D.drawRoundedCorner(x + width - wwidth - 10, y + 2, wwidth, 10, 2, ColorUtil.rgba(20, 21, 24, 255));

        Fonts.msLight[13].drawCenteredString(stack, ClientUtil.getKey(set.getKey()) == null ? "" : ClientUtil.getKey(set.getKey()).toUpperCase(), x + width - wwidth - 10 + wwidth / 2f, y + height / 2f - Fonts.msLight[13].getFontHeight() / 2f + 2, ColorUtil.rgba(161, 166, 179, 255));

    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {

            bindWindow.openAnimation = !bindWindow.openAnimation;
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