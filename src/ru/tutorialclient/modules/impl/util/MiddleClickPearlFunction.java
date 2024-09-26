package ru.tutorialclient.modules.impl.util;

import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.game.EventMouseTick;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.impl.combat.AuraFunction;
import ru.tutorialclient.modules.impl.player.GappleCooldownFunction;
import ru.tutorialclient.util.world.InventoryUtil;

/**
 * @author dedinside
 * @since 07.06.2023
 */
@FunctionAnnotation(name = "MiddleClickPearl", type = Type.Util)
public class MiddleClickPearlFunction extends Function {

    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventMouseTick mouseTick) {
            if (mouseTick.getButton() == 2) {
                handleMouseTickEvent();
            }
        }
    }

    /**
     * ������������ ������� EventMouseTick ��� ������� �� ������ ������ ���� (������ � ����� 2).
     */
    private void handleMouseTickEvent() {
        if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL) && InventoryUtil.getPearls() >= 0) {
            sendHeldItemChangePacket(InventoryUtil.getPearls());

            sendPlayerRotationPacket(mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround());
            useItem(Hand.MAIN_HAND);

            sendHeldItemChangePacket(mc.player.inventory.currentItem);
        }
    }

    /**
     * ���������� ����� ����� ��������� ��������.
     *
     * @param itemSlot ���� ��������
     */
    private void sendHeldItemChangePacket(int itemSlot) {
        mc.player.connection.sendPacket(new CHeldItemChangePacket(itemSlot));
        GappleCooldownFunction cooldown = Managment.FUNCTION_MANAGER.gappleCooldownFunction;
        GappleCooldownFunction.ItemEnum itemEnum = GappleCooldownFunction.ItemEnum.getItemEnum(Items.ENDER_PEARL);

        if (cooldown.state && itemEnum != null && cooldown.isCurrentItem(itemEnum)) {
            cooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
        }
    }

    /**
     * ���������� ����� �������� ������, ���� ������ ���� �� � ������� ������� ��������.
     *
     * @param yaw      �������� �������� �� �����������
     * @param pitch    �������� �������� �� ���������
     * @param onGround �������� ��������� �� ����� �� �����
     */
    private void sendPlayerRotationPacket(float yaw, float pitch, boolean onGround) {
        if (Managment.FUNCTION_MANAGER.auraFunction.target != null) {
            mc.player.connection.sendPacket(new CPlayerPacket.RotationPacket(yaw, pitch, onGround));
        }
    }

    /**
     * ���������� ����� ������������� �������� � ������� ����.
     *
     * @param hand �������� ����
     */
    private void useItem(Hand hand) {
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        mc.player.swingArm(hand);
    }
}
