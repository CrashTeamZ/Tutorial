package ru.tutorialclient.modules.impl.util;

import ru.tutorialclient.events.Event;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.MultiBoxSetting;

/**
 * @author dedinside
 * @since 12.06.2023
 */
@FunctionAnnotation(name = "Optimization", type = Type.Util)
public class Optimization extends Function {

    public final MultiBoxSetting optimizeSelection = new MultiBoxSetting("Оптимизировать", new BooleanOption("Освещение",true), new BooleanOption("Партиклы",true), new BooleanOption("Подсветка клиента.", false));

     public Optimization() {
         addSettings(optimizeSelection);
     }

    @Override
    public void onEvent(Event event) {}
}
