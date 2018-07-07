package net.virushd.coins.main;

import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.coins.commands.Coins;
import net.virushd.coins.events.AchievementEvent;
import net.virushd.coins.events.BlockBreakEvent;
import net.virushd.coins.events.KillEvent;

public class CoinsMain extends JavaPlugin {
	
	public void onEnable() {

		// files
		FileManager.Manager();

		// events
		getServer().getPluginManager().registerEvents(new AchievementEvent(), this);
		getServer().getPluginManager().registerEvents(new KillEvent(), this);
		getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);

		// commands
		getCommand("coins").setExecutor(new Coins());

		// load message
		getLogger().info("Plugin enabled!");
	}
}
