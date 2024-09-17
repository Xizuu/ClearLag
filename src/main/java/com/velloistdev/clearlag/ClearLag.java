package com.velloistdev.clearlag;

import org.bukkit.plugin.java.JavaPlugin;

public final class ClearLag extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new ClearlagTask(this).runTaskTimer(this, 20, 20);
    }
}
