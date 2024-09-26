package ru.tutorialclient.modules.impl.combat;

import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CUseEntityPacket;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.packet.EventPacket;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

/**
 * @author dedinside
 * @since 27.06.2023
 */
@FunctionAnnotation(name = "No Friend Damage", type = Type.Combat)
public class NoFriendDamage extends Function {

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket packet) {
            if (packet.getPacket() instanceof CUseEntityPacket useEntityPacket) {
                Entity entity = useEntityPacket.getEntityFromWorld(mc.world);
                if (entity instanceof RemoteClientPlayerEntity
                        && Managment.FRIEND_MANAGER.isFriend(entity.getName().getString())
                        && useEntityPacket.getAction() == CUseEntityPacket.Action.ATTACK) {
                    event.setCancel(true);
                }
            }
        }
    }
}
