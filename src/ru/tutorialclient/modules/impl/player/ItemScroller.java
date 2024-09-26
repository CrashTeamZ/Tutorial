package ru.tutorialclient.modules.impl.player;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.SliderSetting;

/**
 * @author dedinside
 * @since 25.06.2023
 */

@FunctionAnnotation(name = "ItemScroller", type = Type.Player)
public class ItemScroller extends Function {

    public SliderSetting delay = new SliderSetting("Задержка", 80, 0, 1000, 1);


    public ItemScroller() {
        addSettings(delay);
    }

    @Override
    public void onEvent(Event event) {

    }
}
