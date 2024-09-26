package ru.tutorialclient.modules.impl.player;

import net.minecraft.client.gui.screen.DeathScreen;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
/**
 * @author dedinside
 * @since 04.06.2023
 */
@FunctionAnnotation(name = "AutoRespawn", type = Type.Player)
public class AutoRespawnFunction extends Function {

    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            if (mc.currentScreen instanceof DeathScreen && mc.player.deathTime > 2) {
                mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
            }
        }
    }
}
