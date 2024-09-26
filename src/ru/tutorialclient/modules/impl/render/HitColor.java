package ru.tutorialclient.modules.impl.render;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.SliderSetting;

@FunctionAnnotation(name = "HitColor", type = Type.Render)
public class HitColor extends Function {

    public SliderSetting intensivity = new SliderSetting("Интенсивность", 0.3f, 0.1f, 1, 0.1f);

    public HitColor() {
        super();
        addSettings(intensivity);
    }

    @Override
    public void onEvent(Event event) {

    }
}
