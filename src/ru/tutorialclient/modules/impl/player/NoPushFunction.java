package ru.tutorialclient.modules.impl.player;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.MultiBoxSetting;

/**
 * @author dedinside
 * @since 04.06.2023
 */
@FunctionAnnotation(name = "NoPush", type = Type.Player)
public class NoPushFunction extends Function {

    public final MultiBoxSetting modes = new MultiBoxSetting("Тип",
            new BooleanOption("Игроки", true),
            new BooleanOption("Блоки", true),
            new BooleanOption("Вода", true));

    public NoPushFunction() {
        addSettings(modes);
    }

    @Override
    public void onEvent(final Event event) {
    }
}
