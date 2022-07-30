package com.purityvanilla.puritykits.tasks;

import com.purityvanilla.puritykits.PurityKits;
import org.bukkit.scheduler.BukkitRunnable;


public class UpdateCooldown extends BukkitRunnable {

    @Override
    public void run() {
        PurityKits.INSTANCE.getKitsManager().updateCooldowns();
    }

}
