package com.harbo5.worldboardcast;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

class RepeatBroadcast {
    RepeatBroadcast(final ArrayList<String> messages, final int repeatTime, final int intialDelay, final Main pl, final World world){
       new BukkitRunnable() {
           public void run() {
               for(Player p : world.getPlayers())
                   for(String s : messages)
                       p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
           }
       }.runTaskTimer(pl, intialDelay*20, repeatTime*20);
    }
}
