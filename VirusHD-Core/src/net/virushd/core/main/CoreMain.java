package net.virushd.core.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.core.commands.Admin;
import net.virushd.core.commands.Lobby;
import net.virushd.core.events.ChatEvent;
import net.virushd.core.events.CommandEvent;
import net.virushd.core.events.InvClickEvent;
import net.virushd.core.events.ItemClickEvent;
import net.virushd.core.events.JoinEvent;
import net.virushd.core.events.PlayerDeathEvent;
import net.virushd.core.events.QuitEvent;

public class CoreMain extends JavaPlugin {

	public static CoreMain main;

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
		//getServer().getPluginManager().registerEvents(new DamageEvent(), this);
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
}
