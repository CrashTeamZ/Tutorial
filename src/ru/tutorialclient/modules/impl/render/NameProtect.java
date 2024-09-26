package ru.tutorialclient.modules.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.friend.Friend;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.ModeSetting;
import ru.tutorialclient.modules.settings.imp.TextSetting;
import ru.tutorialclient.util.ClientUtil;

@FunctionAnnotation(name = "NameProtect", type = Type.Render)
public class NameProtect extends Function {

    public TextSetting name = new TextSetting("Ник", "expensive");
    public BooleanOption friends = new BooleanOption("Друзья", false);


    public NameProtect() {
        addSettings(name, friends);
    }

    @Override
    public void onEvent(Event event) {

    }

    public String patch(String text) {
        String out = text;
        if (this.state) {
            out = text.replaceAll(Minecraft.getInstance().session.getUsername(), name.text);
        }
        return out;
    }

    public ITextComponent patchFriendTextComponent(ITextComponent text, String name) {
        ITextComponent out = text;
        if (this.friends.get() && this.state) {
            out = ClientUtil.replace(text, name, this.name.text);
        }
        return out;
    }
}
