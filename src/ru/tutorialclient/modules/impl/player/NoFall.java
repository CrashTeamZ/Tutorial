package ru.tutorialclient.modules.impl.player;

import net.minecraft.network.play.client.CPlayerPacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventMotion;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

import java.util.concurrent.ThreadLocalRandom;

@FunctionAnnotation(name = "NoFall", type = Type.Player)
public class NoFall extends Function {


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMotion e) {
            if (mc.player.ticksExisted % 3 == 0 && mc.player.fallDistance > 3) {
                e.setY(e.getY() + 0.2f);
            }
        }
    }
}
