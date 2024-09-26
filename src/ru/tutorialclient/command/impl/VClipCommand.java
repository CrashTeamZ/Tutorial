package ru.tutorialclient.command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import ru.tutorialclient.command.Command;
import ru.tutorialclient.command.CommandInfo;

import static ru.tutorialclient.util.IMinecraft.mc;

@CommandInfo(name = "vclip", description = "Телепортирует вас вверх.")
public class VClipCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        Minecraft.getInstance().player.setPosition(mc.player.getPosX(), mc.player.getPosY() + Double.parseDouble(args[1]), mc.player.getPosZ());
    }

    @Override
    public void error() {

    }
}
