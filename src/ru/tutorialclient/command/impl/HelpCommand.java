package ru.tutorialclient.command.impl;

import ru.tutorialclient.command.Command;
import ru.tutorialclient.command.CommandInfo;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.util.ClientUtil;

@CommandInfo(name = "help", description = "Телепортирует вас вперед.")
public class HelpCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        for (Command cmd : Managment.COMMAND_MANAGER.getCommands()) {
            if (cmd instanceof HelpCommand) continue;
            ClientUtil.sendMesage(cmd.command + " | " + cmd.description);
        }
    }

    @Override
    public void error() {

    }
}
