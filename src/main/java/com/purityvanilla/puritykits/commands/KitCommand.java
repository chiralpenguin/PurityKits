package com.purityvanilla.puritykits.commands;

import com.purityvanilla.puritykits.gui.KitsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cOnly a player may use this command!");
            return true;
        }

        Player player = (Player) sender;

        KitsGUI kitsGui = new KitsGUI(player);
        kitsGui.openGUI(player);

        return true;
    }
}
