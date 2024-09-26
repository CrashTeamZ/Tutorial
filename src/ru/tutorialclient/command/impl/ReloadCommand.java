package ru.tutorialclient.command.impl;

import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import ru.tutorialclient.command.Command;
import ru.tutorialclient.command.CommandInfo;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.util.math.KeyMappings;

/**
 * @author dedinside
 * @since 25.06.2023
 */
@CommandInfo(name = "reload", description = "Перезагрузка плагина")
public class ReloadCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        Managment.SCRIPT_MANAGER.reload();
        sendMessage("Все скрипты перезагружены.");
    }

    @Override
    public void error() {

    }
}