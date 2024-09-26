package ru.tutorialclient.modules.impl.util;

import net.minecraft.util.text.ITextComponent;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.ButtonSetting;
import ru.tutorialclient.ui.automyst.Window;

@FunctionAnnotation(name = "Auto Buy", type = Type.Util)
public class AutoMist extends Function {

    public ButtonSetting buttonSetting = new ButtonSetting("Открыть панель", () -> {
        mc.displayGuiScreen(new Window(ITextComponent.getTextComponentOrEmpty("")));
    });

    public AutoMist() {
        super();
        addSettings(buttonSetting);
    }

    @Override
    public void onEvent(Event event) {

    }
}
