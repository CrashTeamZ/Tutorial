package ru.tutorialclient.modules.impl.util;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.util.ClientUtil;

@FunctionAnnotation(name = "DiscordRPC", type = Type.Util)
public class DiscordRPC extends Function {

    @Override
    protected void onDisable() {
        super.onDisable();

    }

    @Override
    public void onEvent(Event event) {

    }
}
