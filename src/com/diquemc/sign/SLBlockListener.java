package com.diquemc.sign;

import com.diquemc.chat.ChatChannel;
import com.diquemc.chat.DiqueMCChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;


public class SLBlockListener implements Listener {
    ChatChannel logChannel;
    DiqueMCChat chatPlugin;

    public SLBlockListener() {
        logChannel = new ChatChannel("SignLogger", false, false, null, "&7[SignLog {servername}]");
        chatPlugin = (DiqueMCChat) Bukkit.getPluginManager().getPlugin("DiqueMCChat");

    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onSignChange(SignChangeEvent event) {
        if (event.getLines().length == 0) {
            return;
        }

        if (SignLogger.has(event.getPlayer(), "signlogger.bypass")) {
            return;
        }

        Block block = event.getBlock();

        String header = event.getPlayer().getName();
        String world = " " + block.getWorld().getName();
        String coords = "[" + block.getX() + " " + block.getY() + " " + block.getZ() + world + "]";

        String content = "";

        for (String line : event.getLines()) {
            if (line.trim().length() != 0) {

                if(content.length() == 0){
                    content = line;
                }else {
                    content = content + ChatColor.GRAY + " | " + line;
                }


            }
        }
        if (content.length() == 0) {
            return;
        }

        content = ChatColor.GRAY + " [ " + content + ChatColor.GRAY  + " ] ";
        chatPlugin.chatHandler.playerChat(Bukkit.getConsoleSender(),logChannel,header + content + coords);
    }
}
