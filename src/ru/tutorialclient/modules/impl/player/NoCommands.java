package ru.tutorialclient.modules.impl.player;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;

@FunctionAnnotation(name = "NoCommands", type = Type.Player)
public class NoCommands extends Function {
    @Override
    public void onEvent(Event event) {

    }
}
