package ru.tutorialclient.modules.impl.movement;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventInput;
import ru.tutorialclient.events.impl.player.EventMotion;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BindSetting;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.util.movement.MoveUtil;

/**
 * @author dedinside
 * @since 03.06.2023
 */
@FunctionAnnotation(name = "Sprint", type = Type.Movement)
public class SprintFunction extends Function {

    public BooleanOption keepSprint = new BooleanOption("Keep Sprint", true);

    public SprintFunction() {
        addSettings(keepSprint);
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            // Если игрок не присев и не столкнулся с препятствием по горизонтали
            if (!mc.player.isSneaking() && !mc.player.collidedHorizontally)
                // Устанавливаем режим спринта, если игрок движется
                mc.player.setSprinting(MoveUtil.isMoving());
        }

    }
}
