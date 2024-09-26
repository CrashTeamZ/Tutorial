package ru.tutorialclient.modules.impl.util;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.game.EventKey;
import ru.tutorialclient.events.impl.player.EventInput;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BindSetting;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.movement.MoveUtil;
import ru.tutorialclient.util.world.InventoryUtil;

@FunctionAnnotation(name = "ElytraSwap", type = Type.Util)
public class ElytraSwap extends Function {

    private BindSetting swapKey = new BindSetting("Кнопка свапа", 0);
    private BindSetting fireworkKey = new BindSetting("Кнопка феерверков", 0);
    private ItemStack oldStack = null;
    boolean startFallFlying;
    private BooleanOption autoFly = new BooleanOption("Автоматический взлёт", true);

    private BooleanOption notif = new BooleanOption("Оповещение", true);

    public ElytraSwap() {
        addSettings(swapKey, fireworkKey, notif, autoFly);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (autoFly.get() && mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA) {
                if (mc.player.isOnGround()) {
                    startFallFlying = false;
                    mc.gameSettings.keyBindJump.setPressed(false);
                    mc.player.jump();
                    return;
                }

                if (!mc.player.isElytraFlying() && !startFallFlying && mc.player.motion.y < 0.0) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    startFallFlying = true;
                }
            }
        }
        if (event instanceof EventKey e) {
            ItemStack itemStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);


            if (e.key == swapKey.getKey()) {
                int elytraSlot = InventoryUtil.getItemSlot(Items.ELYTRA);


                if (elytraSlot == -1) {
                    ClientUtil.sendMesage(TextFormatting.RED + "Не найдена элитра в инвентаре!");
                    return;
                }

                if (reasonToEquipElytra(itemStack)) {
                    ItemStack n = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                    oldStack = n.copy();
                    InventoryUtil.moveItem(elytraSlot, 6, true);
                    if (notif.get())
                        ClientUtil.sendMesage(TextFormatting.RED + "Свапнул на элитру!");

                } else if (oldStack != null) {
                    int oldStackSlot = InventoryUtil.getItemSlot(oldStack.getItem());
                    InventoryUtil.moveItem(oldStackSlot, 6, true);
                    if (notif.get())
                        ClientUtil.sendMesage(TextFormatting.RED + "Свапнул на нагрудник!");
                    oldStack = null;

                }
            }
            if (e.key == fireworkKey.getKey() && itemStack.getItem() == Items.ELYTRA) useFirework();
        }
    }

    private void useFirework() {
        int fireWorksSlot = InventoryUtil.getFireWorks();


        boolean offHand = mc.player.getHeldItemOffhand().getItem() == Items.FIREWORK_ROCKET;

        if (!offHand && fireWorksSlot == -1) {
            ClientUtil.sendMesage(TextFormatting.RED + "Нет феерверков!");
            return;
        }

        if (!offHand) mc.player.connection.sendPacket(new CHeldItemChangePacket(fireWorksSlot));
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(offHand ? Hand.OFF_HAND : Hand.MAIN_HAND));
        if (!offHand) mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));

    }

    private boolean reasonToEquipElytra(ItemStack stack) {
        return stack.getItem() != Items.ELYTRA;
    }
}
