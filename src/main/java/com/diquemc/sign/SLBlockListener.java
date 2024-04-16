package com.diquemc.sign;

import com.diquemc.chat.ChatChannel;
import com.diquemc.chat.DiqueMCChat;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.StringJoiner;

public class SLBlockListener implements Listener {
    private ChatChannel logChannel;
    private DiqueMCChat chatPlugin;

    SLBlockListener() {
        logChannel = new ChatChannel("SignLogger", false, false, null, "&7[SignLog {servername}]");
        chatPlugin = com.diquemc.chat.DiqueMCChat.getInstance();

    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLines().length == 0) {
            return;
        }
        Player player = event.getPlayer();
        if (player == null || player.hasPermission("signlogger.bypass")) {
            return;
        }

        Block block = event.getBlock();

        String header = player.getName();
        String world = " " + block.getWorld().getName();
        String coords = "[" + block.getX() + " " + block.getY() + " " + block.getZ() + world + "]";

        StringJoiner lines = new StringJoiner(ChatColor.GRAY + " | ");
        for (String line : event.getLines()) {
            if (line.trim().length() != 0) {
                lines.add(line);
            }
        }
        if (lines.length() == 0) {
            return;
        }
        String content = ChatColor.GRAY + " [ " + lines.toString() + ChatColor.GRAY  + " ] ";
        chatPlugin.chatHandler.playerChat(Bukkit.getConsoleSender(),logChannel,header + content + coords);
    }
}
