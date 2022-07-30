package com.purityvanilla.puritykits.commands;

import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.listeners.PlayerJoinListener;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§cOnly a player may use this command!");
            return true;
        }

        Player player = (Player) sender;

        PlayerJoinListener.resetPlayer(player);

        return true;
    }
}
