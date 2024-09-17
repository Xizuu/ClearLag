package com.velloistdev.clearlag;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ClearlagTask extends BukkitRunnable {

    private final ClearLag plugin;
    private int interval;

    public ClearlagTask(ClearLag plugin) {
        this.plugin = plugin;
        this.interval = plugin.getConfig().getInt("Interval");
    }

    @Override
    public void run() {
        interval--;

        if (interval <= 0) {
            int counter = 0;
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof Item) {
                        if (plugin.getConfig().getBoolean("Settings.Items")) {
                            entity.remove();
                            counter++;
                        }
                    } else if (entity instanceof Mob) {
                        if (plugin.getConfig().getBoolean("Settings.Mobs")) {
                            entity.remove();
                            counter++;
                        }
                    } else if (entity instanceof ExperienceOrb) {
                        if (plugin.getConfig().getBoolean("Settings.ExpOrb")) {
                            entity.remove();
                            counter++;
                        }
                    }
                }
            }
            interval = plugin.getConfig().getInt("Interval");

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Messages.Cleared"))
                        .replace("{PREFIX}", Objects.requireNonNull(plugin.getConfig().getString("Messages.Prefix")))
                        .replace("{COUNT}", String.valueOf(counter))
                ));
            }
        } else if (plugin.getConfig().getIntegerList("Times").contains(interval)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Messages.Running"))
                        .replace("{PREFIX}", Objects.requireNonNull(plugin.getConfig().getString("Messages.Prefix")))
                        .replace("{SECONDS}", Integer.toString(interval))
                ));
            }
        }
    }
}
