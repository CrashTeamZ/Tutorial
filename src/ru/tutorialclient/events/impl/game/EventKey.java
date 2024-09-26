package ru.tutorialclient.events.impl.game;

import ru.tutorialclient.events.Event;

public class EventKey extends Event {

    public int key;

    public EventKey(int key) {
        this.key = key;
    }
}
