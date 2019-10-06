package com.diquemc.sign;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SignLogger extends JavaPlugin {
    private final SLBlockListener blockListener = new SLBlockListener();

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this.blockListener, this);
        loadConfiguration();
        getLogger().info(" version [" + getDescription().getVersion() + "] loaded");
    }

    private void loadConfiguration() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
    }
}
