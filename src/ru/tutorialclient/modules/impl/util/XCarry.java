package ru.tutorialclient.modules.impl.util;

import net.minecraft.network.play.client.CCloseWindowPacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

@FunctionAnnotation(name = "XCarry", type = Type.Util)
public class XCarry extends Function {


    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket) {
            if (((EventPacket) event).getPacket() instanceof CCloseWindowPacket) {
                event.setCancel(true);
            }
        }
    }
}
