package ru.tutorialclient.events.impl.player;

import ru.tutorialclient.events.Event;

public class EventTravel extends Event {

    public float speed;

    public EventTravel(float speed) {
        this.speed = speed;
    }

}
