package ru.tutorialclient.events.impl.player;

import net.minecraft.client.renderer.entity.PlayerRenderer;
import ru.tutorialclient.events.Event;

public class EventModelRender extends Event {

    public PlayerRenderer renderer;
    private Runnable entityRenderer;

    public EventModelRender(PlayerRenderer renderer, Runnable entityRenderer) {
        this.renderer = renderer;
        this.entityRenderer = entityRenderer;
    }

    public void render() {
        entityRenderer.run();
    }

}
