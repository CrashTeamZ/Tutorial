package ru.tutorialclient.modules.impl.util;

import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.events.impl.player.EventTeleport;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.ModeSetting;
import ru.tutorialclient.util.ClientUtil;

/**
 * @author dedinside
 * @since 07.06.2023
 */
@FunctionAnnotation(name = "No Server Rot", type = Type.Util)
public class NoServerRotFunction extends Function {
    private ModeSetting serverRotMode = new ModeSetting("Тип", "Обычный", "Обычный", "RW");

    public NoServerRotFunction() {
        addSettings(serverRotMode);
    }

    @Override
    public void onEvent(final Event event) {
        if (!serverRotMode.is("RW")) {
            if (event instanceof EventPacket packet) {
                if (packet.isReceivePacket()) {
                    if (packet.getPacket() instanceof SPlayerPositionLookPacket packet1) {
                        packet1.yaw = mc.player.rotationYaw;
                        packet1.pitch = mc.player.rotationPitch;
                    }
                }
            }
        }
    }
}
