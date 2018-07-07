package net.virushd.creative.main;

import java.util.ArrayList;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.creative.commands.Creative;
import net.virushd.creative.events.ChatEvent;
import net.virushd.creative.events.CommandEvent;
import net.virushd.creative.events.PlayerDeathEvent;
import net.virushd.creative.events.QuitEvent;
import net.virushd.creative.events.SignEvent;

import net.virushd.core.main.PlaceHolder;

public class CreativeMain extends JavaPlugin {
	
	public static CreativeMain main;

	public static ArrayList<Player> players = new ArrayList<>();

	public void onEnable() {
		
		// instance
		main = this;

		// files
		FileManager.Manager();

		// commands
		getCommand("creative").setExecutor(new Creative());

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
				signs.setLine(i, PlaceHolder.CreativeSign(FileManager.config.getString("Sign.Lines." + (i + 1)).replace("{Players}", "" + 0)));
				signs.update();
			}
		}
	}
}
