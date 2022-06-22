package com.purityvanilla.puritykits.commands;

import com.purityvanilla.puritykits.PurityKits;
import com.purityvanilla.puritykits.gui.KitsGUI;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
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

        if (args.length == 0) {
            KitsGUI kitsGui = new KitsGUI(player);
            kitsGui.openGUI(player);
        } else {
            try {
                int kitNumber = Integer.parseInt(args[0]);
                if (kitNumber > 7) {
                    player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                            PurityKits.config.KitClaimFailMessage()
                    ));
                    return true;
                }
                PurityKits.INSTANCE.getKitsManager().claimKit(player, kitNumber);
            } catch (NumberFormatException e) {
                player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
                        PurityKits.config.KitClaimFailMessage()
                ));
            }
        }


        return true;
    }
}
