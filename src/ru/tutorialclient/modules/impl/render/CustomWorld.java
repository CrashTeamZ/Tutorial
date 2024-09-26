package ru.tutorialclient.modules.impl.render;

import net.minecraft.network.play.server.SUpdateTimePacket;
import net.minecraft.util.math.MathHelper;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.*;
import ru.tutorialclient.util.animations.Animation;
import ru.tutorialclient.util.math.MathUtil;
import ru.tutorialclient.util.render.animation.AnimationMath;

/**
 * @author dedinside
 * @since 26.06.2023
 */
@FunctionAnnotation(name = "Custom World", type = Type.Render)
public class CustomWorld extends Function {

    public MultiBoxSetting modes = new MultiBoxSetting("��������",
            new BooleanOption("�����", false),
            new BooleanOption("�����", false));

    private ModeSetting timeOfDay = new ModeSetting("����� �����", "����", "����", "�����", "�������", "����", "�������", "�������").setVisible(() -> modes.get(0));

    public ColorSetting colorFog = new ColorSetting("���� ������", -1).setVisible(() -> modes.get(1));
    public SliderSetting distanceFog = new SliderSetting("��������� ������", 4.0F, 1.1f, 30.0F, 0.01f).setVisible(() -> modes.get(1));

    public CustomWorld() {
        addSettings(modes, timeOfDay, colorFog, distanceFog);
    }

    float time;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket eventPacket && ((EventPacket) event).isReceivePacket()) {
            if (eventPacket.getPacket() instanceof SUpdateTimePacket) {
                if (modes.get(0)) {
                    eventPacket.setCancel(true);
                }
            }
        }
        if (event instanceof EventUpdate) {
            if (modes.get(0)) {
                float time = 0;
                switch (timeOfDay.get()) {
                    case "����" -> time = 1000;
                    case "�����" -> time = 12000;
                    case "�������" -> time = 23000;
                    case "����" -> time = 13000;
                    case "�������" -> time = 18000;
                    case "�������" -> time = 6000;
                }
                mc.world.setDayTime((long) time);
            }
        }
    }
}
