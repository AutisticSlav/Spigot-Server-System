package net.virushd.core.main;

import net.virushd.core.api.ConfigFile;
import net.virushd.core.api.ConfigFile.FileType;
import net.virushd.core.api.Minigame;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.events.*;
import net.virushd.inventory.main.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.core.commands.Admin;
import net.virushd.core.commands.Lobby;

import java.util.ArrayList;
import java.util.Arrays;

import static net.virushd.core.main.FileManager.inv_teleporter;
import static net.virushd.core.main.FileManager.locations;

public class CoreMain extends JavaPlugin {

	public static CoreMain main;
	private static ArrayList<Minigame> minigames = new ArrayList<>();

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new ItemClickEvent(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new InvClickEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);

		// commands
		getCommand("lobby").setExecutor(new Lobby());
		getCommand("admin").setExecutor(new Admin());

		// updater
		Updater.scoreboardUpdater();
		Updater.randomMessages();
		Updater.playerVisibility();
		Updater.antiRespawnBug();

		// console filter
		ConsoleFilter.addFilter("issued server command");
		ConsoleFilter.addFilter("moved wrongly");
		ConsoleFilter.addFilter("logged in with entity id");
		ConsoleFilter.addFilter("uuid of player");
		ConsoleFilter.addFilter("lost connection");
		ConsoleFilter.start();

		// load message
		getLogger().info("Plugin enabled!");
	}

	public void onDisable() {

		// kick the players on reload or stop
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("Server Stopped"); // TODO make the message custom
		}
	}

	// check if debug mode is active
	public static boolean debug() {
		return FileManager.config.getBoolean("DebugMode");
	}

	// check if a plugin is available
	public static boolean pluginAvailable(String pl) {
		return Bukkit.getServer().getPluginManager().getPlugin(pl) != null;
	}

	// register a minigame
	public static void registerMinigame(Minigame minigame) {
		minigames.add(minigame);
		SaveUtils.defaultItemToFile(inv_teleporter, "Items." + minigame.getRealName(), minigame.getDefaultItem());
		inv_teleporter.addDefault("Items." + minigame.getRealName() + ".Slot", minigame.getDefaultSlot());
		SaveUtils.defaultLocationToFile(locations, minigame.getRealName(), minigame.getDefaultLocation());
	}

	// get a copy of the minigame list
	public static ArrayList<Minigame> getMinigames() {
		return new ArrayList<>(minigames);
	}
}
