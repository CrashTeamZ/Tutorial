package org.luaj.vm2.customs;

import ru.tutorialclient.events.Event;

public class EventHook {

    public Event event;

    public EventHook(Event event) {
        this.event = event;
    }

    public String getName() {
        return "default";
    }

}
