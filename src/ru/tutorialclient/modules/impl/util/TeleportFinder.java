package ru.tutorialclient.modules.impl.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.*;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

import java.lang.reflect.Method;

@FunctionAnnotation(name = "Teleport Finder", type = Type.Util)
public class TeleportFinder extends Function {
    @Override
    public void onEvent(Event event) {

        if (event instanceof EventPacket e) {
           if (e.isReceivePacket()) {
               IPacket<?> packet = e.getPacket();

               for (Method m : packet.getClass().getMethods()) {
                   if (m.getName().toLowerCase().contains("entityid")) {
                       System.out.println(packet);
                   }
               }
           }
        }

    }
}
