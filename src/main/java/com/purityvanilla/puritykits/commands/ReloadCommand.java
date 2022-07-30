package com.purityvanilla.puritykits.commands;

import com.purityvanilla.puritykits.Config;
import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.tasks.UpdateCooldown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        sender.sendMessage("\247bReloading PurityKits...");
        PurityKits.config = new Config();

        if (PurityKits.config.cooldownEnabled()){
            PurityKits.INSTANCE.updateCooldownTask.cancel();
            PurityKits.INSTANCE.updateCooldownTask = new UpdateCooldown().runTaskTimer(PurityKits.INSTANCE, 20L, 20L);
        }

        sender.sendMessage("\247bPurityKits config reloaded!");
        return true;
    }
}
