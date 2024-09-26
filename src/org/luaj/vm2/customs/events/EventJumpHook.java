package org.luaj.vm2.customs.events;

import org.luaj.vm2.customs.EventHook;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.EventJump;
import ru.tutorialclient.events.impl.player.EventMotion;

public class EventJumpHook extends EventHook {

    private EventJump jump;

    public EventJumpHook(Event event) {
        super(event);
        this.jump = (EventJump) event;
    }




    @Override
    public String getName() {
        return "jump_event";
    }
}
