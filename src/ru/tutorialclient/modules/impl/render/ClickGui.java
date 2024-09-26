package ru.tutorialclient.modules.impl.render;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.SliderSetting;

@FunctionAnnotation(name = "Click Gui", type = Type.Render)
public class ClickGui extends Function {


    public BooleanOption blur = new BooleanOption("��������", true);
    public SliderSetting blurVal = new SliderSetting("���� ��������", 15, 5, 20, 1);
    public BooleanOption glow = new BooleanOption("��������", false);

    public ClickGui() {
        super();
        addSettings(blur,blurVal,glow);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        setState(false);
    }

    @Override
    public void onEvent(Event event) {

    }
}
