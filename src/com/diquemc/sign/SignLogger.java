package com.diquemc.sign;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SignLogger extends JavaPlugin {
    private final SLBlockListener blockListener = new SLBlockListener();

    public static boolean has(Player player, String perm) {
        return (player == null) || player.hasPermission(perm);
    }
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this.blockListener, this);
        loadConfiguration();
        getLogger().info("[" + getDescription().getName() + "] version [" + getDescription().getVersion() + "] loaded");
    }

    public void loadConfiguration() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
    }
}
