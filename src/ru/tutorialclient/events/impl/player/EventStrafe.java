package ru.tutorialclient.events.impl.player;

import ru.tutorialclient.events.Event;

public class EventStrafe extends Event {

    public float yaw;

    public EventStrafe(float yaw) {
        this.yaw = yaw;
    }

}
