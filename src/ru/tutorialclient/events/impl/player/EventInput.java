package ru.tutorialclient.events.impl.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.tutorialclient.events.Event;
@Getter
@Setter
@AllArgsConstructor
public class EventInput extends Event {
    private float forward, strafe;
    private boolean jump, sneak;
    private double sneakSlowDownMultiplier;



}
