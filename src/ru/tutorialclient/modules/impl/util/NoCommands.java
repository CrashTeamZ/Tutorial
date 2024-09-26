package ru.tutorialclient.modules.impl.util;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

/**
 * @author dedinside
 * @since 12.07.2023
 */
@FunctionAnnotation(name = "NoCommands", type = Type.Util)
public class NoCommands extends Function {
    @Override
    public void onEvent(Event event) {

    }
}
