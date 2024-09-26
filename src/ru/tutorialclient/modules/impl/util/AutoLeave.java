package ru.tutorialclient.modules.impl.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CChatMessagePacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventMotion;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.ModeSetting;
import ru.tutorialclient.modules.settings.imp.SliderSetting;
import ru.tutorialclient.util.ClientUtil;

import java.awt.*;

@FunctionAnnotation(name = "AutoLeave", type = Type.Util)
public class AutoLeave extends Function {

    public SliderSetting range = new SliderSetting("���������", 15, 5, 40, 1);
    public ModeSetting mode = new ModeSetting("��� ������?", "/spawn", "/spawn", "/hub", "kick");
    public BooleanOption health = new BooleanOption("�� ��������", false);
    public SliderSetting healthSlider = new SliderSetting("��������", 10, 5, 20, 1).setVisible(() -> health.get());

    public AutoLeave() {
        addSettings(range, mode, health, healthSlider);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventMotion e) {
            if (health.get()) {
                if (mc.player.getHealth() <= healthSlider.getValue().floatValue()) {
                    if (mode.is("kick")) {
                        mc.player.connection.getNetworkManager().closeChannel(ClientUtil.gradient("�� ����� � �������! \n" +" ���� ��!", new Color(121, 208, 255).getRGB(), new Color(96, 133, 255).getRGB()));
                    } else {
                        mc.player.connection.sendPacket(new CChatMessagePacket(mode.get()));
                    }
                }
                setState(false);
                return;
            }

            for (PlayerEntity player : mc.world.getPlayers()) {
                if (player == mc.player) continue;
                if (player.isBot) continue;
                if (Managment.FRIEND_MANAGER.isFriend(player.getGameProfile().getName())) {
                    continue;
                }

                if (mc.player.getDistance(player) <= range.getValue().floatValue()) {
                    if (mode.is("kick")) {
                        mc.player.connection.getNetworkManager().closeChannel(ClientUtil.gradient("�� ����� � �������! \n" + player.getGameProfile().getName(), new Color(121, 208, 255).getRGB(), new Color(96, 133, 255).getRGB()));
                    } else {
                        mc.player.connection.sendPacket(new CChatMessagePacket(mode.get()));
                    }
                    setState(false);
                    break;
                }
            }
        }
    }
}
