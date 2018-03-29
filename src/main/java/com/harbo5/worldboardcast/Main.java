package com.harbo5.worldboardcast;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        final ArrayList<TimeBroadcast> tbList = new ArrayList<>();
        for(String s : getConfig().getConfigurationSection("messages").getKeys(false)){
            String path = "messages." + s;
            String type = getConfig().getString(path+ ".type");
            ArrayList<String> messages = new ArrayList<>();
            messages.addAll(getConfig().getStringList(path + ".message"));
            World world = Bukkit.getWorld(getConfig().getString(path+".world"));
            if(world==null){
                getLogger().log(Level.SEVERE, ChatColor.RED + "[WorldBroadcast] Error: Message #" + s + " cannot be loaded because the world is invalid.");
                continue;
            }
            if(type.equalsIgnoreCase("repeat")){
                new RepeatBroadcast(messages, getConfig().getInt(path+".repeatTime"), getConfig().getInt(path+".intialDelay"), this, world);
            } else if (type.equalsIgnoreCase("time")){
                tbList.add(new TimeBroadcast(messages, getConfig().getInt(path+".executeTime"), world));

            }

        }
        new BukkitRunnable() {
            public void run() {
                Calendar c = Calendar.getInstance();
                long now = c.getTimeInMillis();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                long passed = now - c.getTimeInMillis();
                long secondsPassed = passed / 1000;

                for(TimeBroadcast tb : tbList){
                    if((int)secondsPassed==tb.getTime()){
                        tb.sendMessage();
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

}
