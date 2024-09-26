package ru.tutorialclient.modules.impl.movement;

import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.Heightmap;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.events.impl.player.EventMotion;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.ModeSetting;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.math.MathUtil;
import ru.tutorialclient.util.misc.ServerUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@FunctionAnnotation(name = "KTLeave", type = Type.Movement)
public class KTLeave extends Function {

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (ServerUtil.isHW()) {
                if (e.getPacket() instanceof SEntityVelocityPacket p) {
                    if (p.getEntityID() == mc.player.getEntityId()) {
                        leaveHoly();
                    }
                }
            } else {
                ClientUtil.sendMesage(TextFormatting.RED + "Данная функция работает только на Holyworld!");
                toggle();
            }
        }

    }


    private void leaveHoly() {
        float x = (float) mc.player.getPosX() + ThreadLocalRandom.current().nextFloat(-50, 50);
        float z = (float) mc.player.getPosZ() + ThreadLocalRandom.current().nextFloat(-50, 50);

        float y = mc.world.getHeight(Heightmap.Type.WORLD_SURFACE, (int) x, (int) z) - 1;
        ClientUtil.sendMesage("Телепортирую...");

        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), -180, 0, false));
        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), -180, 0, false));

        for (int i = 0; i <= 10; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(x, y, z, true));
        }
        toggle();
    }
}
