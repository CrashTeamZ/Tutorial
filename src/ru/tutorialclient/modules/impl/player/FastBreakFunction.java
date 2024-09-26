package ru.tutorialclient.modules.impl.player;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
/**
 * @author dedinside
 * @since 04.06.2023
 */
@FunctionAnnotation(name = "FastBreak", type = Type.Player)
public class FastBreakFunction extends Function {

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            // ���������� �������� ����� ����� ��� ������
            mc.playerController.blockHitDelay = 0;

            // ���������, ��������� �� ������� ���� ����� �������� 1.0F
            if (mc.playerController.curBlockDamageMP > 1.0F) {
                // ���� ���������, ������������� �������� ����� ����� ������ 1.0F
                mc.playerController.curBlockDamageMP = 1.0F;
            }
        }
    }
}
