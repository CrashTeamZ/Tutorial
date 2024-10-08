package ru.tutorialclient.modules.impl.util;

import net.minecraft.network.IPacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.events.impl.player.EventMotion;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.SliderSetting;

import java.util.concurrent.ConcurrentLinkedQueue;

@FunctionAnnotation(name = "SlowPackets", type = Type.Util)
public class SlowPackets extends Function {

    private SliderSetting delay = new SliderSetting("��������", 1000,100,5000, 100);

    public SlowPackets() {
        super();
        addSettings(delay);
    }

    public static final ConcurrentLinkedQueue<Scaffold.TimedPacket> packets = new ConcurrentLinkedQueue<>();

    @Override
    protected void onDisable() {
        super.onDisable();
        for (Scaffold.TimedPacket p : packets) {
            mc.player.connection.getNetworkManager().sendPacketWithoutEvent(p.getPacket());
        }
        packets.clear();

    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (e.isSendPacket()) {
                IPacket<?> packet = e.getPacket();
                packets.add(new Scaffold.TimedPacket(packet, System.currentTimeMillis()));
                e.setCancel(true);
            }
        }
        if (event instanceof EventMotion e) {
            for (Scaffold.TimedPacket timedPacket : packets) {
                if (System.currentTimeMillis() - timedPacket.getTime() >= delay.getValue().intValue()) {
                    mc.player.connection.getNetworkManager().sendPacketWithoutEvent(timedPacket.getPacket());
                    packets.remove(timedPacket);
                }
            }
        }
    }
}
