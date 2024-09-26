package ru.tutorialclient.command.impl;

import net.minecraft.util.text.TextFormatting;
import ru.tutorialclient.command.Command;
import ru.tutorialclient.command.CommandInfo;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.util.ClientUtil;

@CommandInfo(name = "t", description = "��������/��������� ������.")
public class ToggleCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        if (args.length == 2) {
            Function func = Managment.FUNCTION_MANAGER.get(args[1]);
            func.setState(!func.isState());

            if (func.isState()) ClientUtil.sendMesage(TextFormatting.GREEN + "������ " + func.name + " �������.");
            else ClientUtil.sendMesage(TextFormatting.RED + "������ " + func.name + " ��������.");
        } else {
            ClientUtil.sendMesage(TextFormatting.RED + "�� ������� �������� �������� ������!");
        }
    }

    @Override
    public void error() {

    }
}
