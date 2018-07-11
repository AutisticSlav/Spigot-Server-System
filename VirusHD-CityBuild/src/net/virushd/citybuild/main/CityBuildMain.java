package net.virushd.citybuild.main;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.virushd.citybuild.commands.CityBuild;
import net.virushd.citybuild.events.ChatEvent;
import net.virushd.citybuild.events.CommandEvent;
import net.virushd.citybuild.events.PlayerDeathEvent;
import net.virushd.citybuild.events.QuitEvent;
import net.virushd.citybuild.events.SignEvent;
import net.virushd.core.main.PlaceHolder;

public class CityBuildMain extends JavaPlugin {
	
	public static CityBuildMain main;
	
	public static ArrayList<Player> players = new ArrayList<>();
	
	public void onEnable() {
		
		// instance
		main = this;

		// files
		FileManager.Manager();

		// commands
		getCommand("citybuild").setExecutor(new CityBuild());

		// events
//		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);

		// updater
		Updater.ScoreboardUpdater();
		Updater.PlayerVisibility();
		Updater.SignUpdater();
		
		// load message
		getLogger().info("Plugin enabled!");
	}
	
	public void onDisable() {
		
		// anti sign bug
		for (Sign signs : Updater.UpdateSigns) {
			for (int i = 0; i < 4; i++) {
				signs.setLine(i, PlaceHolder.CityBuildSign(FileManager.config.getString("Sign.Lines." + (i + 1)).replace("{Players}", "" + 0)));
				signs.update();
			}
		}
	}

	public static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (!(plugin instanceof WorldGuardPlugin)) {
			System.out.println(PlaceHolder.Normal("{CityBuildPrefix}&4Please install WorldGuard!"));
			return null; // Maybe you want throw an exception instead
		}

		return (WorldGuardPlugin) plugin;
	}

/*	public static WorldEditPlugin getWorldEdit() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

		// WorldEdit may not be loaded
		if (!(plugin instanceof WorldEditPlugin)) {
			System.out.println(PlaceHolder.Normal("{CityBuildPrefix}&4Please install WorldEdit!"));
			return null; // Maybe you want throw an exception instead
		}

		return (WorldEditPlugin) plugin;
	}*/
}
