package ru.tutorialclient.modules.impl.render;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.render.EventRender;
import ru.tutorialclient.modules.Function;

public class RegionRender extends Function {
    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender e) {
            if (e.isRender3D()) {

            }
        }
    }
}
