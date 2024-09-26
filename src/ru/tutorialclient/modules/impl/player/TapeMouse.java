package ru.tutorialclient.modules.impl.player;

import net.minecraft.util.math.RayTraceResult;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

@FunctionAnnotation(name = "TapeMouse", type = Type.Player)
public class  TapeMouse extends Function {


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate e) {
            if (mc.player.getCooledAttackStrength(1f) >= 1) {
                mc.clickMouse();
            }
        }
    }
}
