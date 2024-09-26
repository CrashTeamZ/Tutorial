package ru.tutorialclient;

import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.shaders.Shaders;
import org.lwjgl.glfw.GLFW;
import ru.tutorialclient.command.CommandManager;
import ru.tutorialclient.command.macro.MacroManager;
import ru.tutorialclient.config.ConfigManager;
import ru.tutorialclient.config.LastAccountConfig;
import ru.tutorialclient.events.EventManager;
import ru.tutorialclient.events.impl.game.EventKey;
import ru.tutorialclient.friend.FriendManager;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.managment.StaffManager;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionManager;
import ru.tutorialclient.modules.impl.util.UnHookFunction;
import ru.tutorialclient.notification.NotificationManager;
import ru.tutorialclient.proxy.ProxyConnection;
import ru.tutorialclient.scripts.ScriptManager;
import ru.tutorialclient.ui.alt.AltConfig;
import ru.tutorialclient.ui.alt.AltManager;
import ru.tutorialclient.ui.clickgui.Window;
import ru.tutorialclient.ui.midnight.StyleManager;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.DiscordWebhook;
import ru.tutorialclient.util.drag.DragManager;
import ru.tutorialclient.util.drag.Dragging;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.ShaderUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;

public class Initilization {
    public static boolean isServer;

    private static DiscordWebhook webhook = new DiscordWebhook("");

    public final File dir = new File(Minecraft.getInstance().gameDir, "\\expensive");

    public void init() {
    
        long start = System.currentTimeMillis();

        ShaderUtil.init();


        Managment.FUNCTION_MANAGER = new FunctionManager();
        Managment.SCRIPT_MANAGER = new ScriptManager();
        Managment.SCRIPT_MANAGER.parseAllScripts();
        Managment.SCRIPT_MANAGER.init();
        Managment.NOTIFICATION_MANAGER = new NotificationManager();

        try {
            Managment.STYLE_MANAGER = new StyleManager();
            Managment.STYLE_MANAGER.init();


            Managment.ALT = new AltManager();

            if (!dir.exists()) {
                dir.mkdirs();
            }
            Managment.ALT_CONFIG = new AltConfig();
            Managment.ALT_CONFIG.init();

            Managment.FRIEND_MANAGER = new FriendManager();
            Managment.FRIEND_MANAGER.init();

            Managment.COMMAND_MANAGER = new CommandManager();
            Managment.COMMAND_MANAGER.init();

            Managment.STAFF_MANAGER = new StaffManager();
            Managment.STAFF_MANAGER.init();

            Managment.MACRO_MANAGER = new MacroManager();
            Managment.MACRO_MANAGER.init();

            Managment.LAST_ACCOUNT_CONFIG = new LastAccountConfig();
            Managment.LAST_ACCOUNT_CONFIG.init();

            Managment.CONFIG_MANAGER = new ConfigManager();
            Managment.CONFIG_MANAGER.init();

            Managment.CLICK_GUI = new Window(new StringTextComponent("A"));
            DragManager.load();

            Managment.PROXY_CONN = new ProxyConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

       
        System.out.println("Время: " + (System.currentTimeMillis() - start));
        ClientUtil.startRPC();

        DiscordWebhook.EmbedObject embedObject = getEmbedObject();
        webhook.addEmbed(embedObject);
        try {
            webhook.execute();
        } catch (IOException e) {}
    }

    private static DiscordWebhook.EmbedObject getEmbedObject() {
        DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

        embedObject.addField("user", Managment.USER_PROFILE.getName(), true);
        embedObject.addField("uid", String.valueOf(Managment.USER_PROFILE.getUid()), true);
        embedObject.addField("discord", ClientUtil.me == null ? "null" : ClientUtil.me.getName(), true);
        embedObject.setColor(new Color(105, 231, 160));
        if (ClientUtil.me != null)
            embedObject.setImage(ClientUtil.me.getAvatarUrl());
        return embedObject;
    }


    public static void shutDown() {
        System.out.println("TutorialClient Shutdown!");
        Managment.LAST_ACCOUNT_CONFIG.updateFile();
        DragManager.save();
        Managment.CONFIG_MANAGER.saveConfiguration("autocfg");
    }


    public void keyPress(int key) {
        EventManager.call(new EventKey(key));
        if (key == Managment.FUNCTION_MANAGER.unhook.unHookKey.getKey() && ClientUtil.legitMode) {

            ClientUtil.startRPC();
            for (int i = 0; i < UnHookFunction.functionsToBack.size(); i++) {
                Function function = UnHookFunction.functionsToBack.get(i);
                function.setState(true);
            }

            File folder = new File("C:\\Expensive");
            if (folder.exists()) {
                try {
                    Path folderPathObj = folder.toPath();
                    DosFileAttributeView attributes = Files.getFileAttributeView(folderPathObj, DosFileAttributeView.class);
                    attributes.setHidden(false);
                } catch (IOException e) {
                    System.out.println("Ошибка при скрытии папки: " + e.getMessage());
                }
            }
            Minecraft.getInstance().fileResourcepacks = GameConfiguration.gameConfiguration.folderInfo.resourcePacksDir;
            Shaders.shaderPacksDir = new File(Minecraft.getInstance().gameDir, "shaderpacks");
            UnHookFunction.functionsToBack.clear();
            ClientUtil.legitMode = false;
        }
        if (!ClientUtil.legitMode) {
            if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                Minecraft.getInstance().displayGuiScreen(Managment.CLICK_GUI);
            }
            if (Managment.MACRO_MANAGER != null) {
                Managment.MACRO_MANAGER.onKeyPressed(key);
            }
            for (Function m : Managment.FUNCTION_MANAGER.getFunctions()) {
                if (m.bind == key) {
                    m.toggle();
                }
            }
        }
    }

    public static Dragging createDrag(Function module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }
}
