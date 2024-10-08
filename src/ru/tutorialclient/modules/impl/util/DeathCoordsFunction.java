package ru.tutorialclient.modules.impl.util;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.util.text.TextFormatting;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.util.ClientUtil;

/**
 * @author dedinside
 * @since 12.06.2023
 */
@FunctionAnnotation(name = "DeathCoords", type = Type.Util)
public class DeathCoordsFunction extends Function {
    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            checkDeathCoordinates();
        }
    }

    public void checkDeathCoordinates() {
        if (isPlayerDead()) {
            int positionX = mc.player.getPosition().getX();
            int positionY = mc.player.getPosition().getY();
            int positionZ = mc.player.getPosition().getZ();

            if (mc.player.deathTime < 1) {
                printDeathCoordinates(positionX, positionY, positionZ);
            }
        }
    }

    private boolean isPlayerDead() {
        return mc.player.getHealth() < 1.0f && mc.currentScreen instanceof DeathScreen;
    }

    private void printDeathCoordinates(int x, int y, int z) {
        String message = "���������� ������: " + TextFormatting.GRAY + "X: " + x + " Y: " + y + " Z: " + z + TextFormatting.RESET;
        ClientUtil.sendMesage(message);
    }
}
