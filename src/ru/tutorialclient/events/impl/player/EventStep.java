package ru.tutorialclient.events.impl.player;

import ru.tutorialclient.events.Event;

public class EventStep extends Event {

    public float stepHeight;

    public EventStep(float stepHeight) {
        this.stepHeight = stepHeight;
    }

}
