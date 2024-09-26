package ru.tutorialclient.managment;

import ru.tutorialclient.command.CommandManager;
import ru.tutorialclient.command.macro.MacroManager;
import ru.tutorialclient.config.ConfigManager;
import ru.tutorialclient.config.LastAccountConfig;
import ru.tutorialclient.friend.FriendManager;
import ru.tutorialclient.modules.FunctionManager;
import ru.tutorialclient.notification.NotificationManager;
import ru.tutorialclient.proxy.ProxyConnection;
import ru.tutorialclient.scripts.ScriptManager;
import ru.tutorialclient.ui.alt.AltConfig;
import ru.tutorialclient.ui.alt.AltManager;
import ru.tutorialclient.ui.clickgui.Window;
import ru.tutorialclient.ui.midnight.StyleManager;
import ru.tutorialclient.util.UserProfile;

public class Managment {

    public static FunctionManager FUNCTION_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    public static MacroManager MACRO_MANAGER;
    public static LastAccountConfig LAST_ACCOUNT_CONFIG;
    public static ScriptManager SCRIPT_MANAGER;

    public static StaffManager STAFF_MANAGER;
    public static Window CLICK_GUI;
    public static ConfigManager CONFIG_MANAGER;
    public static StyleManager STYLE_MANAGER;
    public static UserProfile USER_PROFILE;
    public static NotificationManager NOTIFICATION_MANAGER;
    public static AltManager ALT;
    public static AltConfig ALT_CONFIG;

    public static ProxyConnection PROXY_CONN;
}
