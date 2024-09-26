package ru.tutorialclient.modules.impl.util;

import net.minecraft.client.util.InputMappings;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.glfw.GLFW;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.misc.TimerUtil;
import ru.tutorialclient.util.movement.MoveUtil;

/**
 * @author dedinside
 * @since 12.06.2023
 */
@FunctionAnnotation(name = "AntiAFK", type = Type.Util)
public class AntiAFKFunction extends Function {

    private final TimerUtil timerUtil = new TimerUtil();

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {

            if (!MoveUtil.isMoving()) {
                if (timerUtil.hasTimeElapsed(15000)) {
                    mc.player.sendChatMessage("/BEFF LOX");
                    timerUtil.reset();
                }
            } else {
                timerUtil.reset();
            }
        }
    }
}
