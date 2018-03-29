package com.harbo5.worldboardcast;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

class TimeBroadcast {
    private ArrayList<String> messages;
    private int time;
    private World world;

    TimeBroadcast(ArrayList<String> m, int t, World w) {
        messages=m;
        time=t;
        world=w;
    }

    void sendMessage() {
        for(Player p : world.getPlayers())
            for(String s : messages)
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    int getTime() {
        return time;
    }
}
