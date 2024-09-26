package ru.tutorialclient.modules.impl.player;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventUpdate;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.MultiBoxSetting;

@FunctionAnnotation(name = "NoDelay", type = Type.Player)
public class NoDelay extends Function {

    private final MultiBoxSetting actions = new MultiBoxSetting("Действия",
            new BooleanOption("Прыжок", true),
            new BooleanOption("Ставить", false)
    );

    public NoDelay() {
        addSettings(actions);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (actions.get(0)) mc.player.jumpTicks = 0;
            if (actions.get(1)) mc.rightClickDelayTimer = 0;
        }
    }
}
