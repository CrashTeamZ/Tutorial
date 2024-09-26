package ru.tutorialclient.command.impl;

import com.mojang.datafixers.types.Func;
import ru.tutorialclient.command.Command;
import ru.tutorialclient.command.CommandInfo;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.util.ClientUtil;

@CommandInfo(name = "panic", description = "Выключает все функции чита")

public class PanicCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        if (args.length == 1) {
            Managment.FUNCTION_MANAGER.getFunctions().stream().filter(function -> function.state).forEach(function -> function.setState(false));
            ClientUtil.sendMesage("Выключил все модули!");
        } else error();
    }

    @Override
    public void error() {

    }
}
