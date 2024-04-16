package com.diquemc.sign;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SignLogger extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SLBlockListener(), this);
        loadConfiguration();
        getLogger().info(" version [" + getDescription().getVersion() + "] loaded");
    }

    private void loadConfiguration() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
    }
}
