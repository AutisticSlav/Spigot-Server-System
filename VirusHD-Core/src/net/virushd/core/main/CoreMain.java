package net.virushd.core.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import net.virushd.core.events.WorldChangeEvent;
import net.virushd.core.scoreboards.NoScoreboard;
import net.virushd.title.title.Title;

public class CoreMain extends JavaPlugin {

	public static CoreMain main;

	private static ArrayList<Player> players = new ArrayList<>();
	private static HashMap<Player, Mode> mode = new HashMap<>();

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// events
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
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

	// get the players
	public static ArrayList<Player> getPlayers() {
		return players;
	}

	// check if debug mode is active
	public static boolean debug() {
		return FileManager.config.getBoolean("DebugMode");
	}

	// set to normal mode
	public static void setNormal(Player p) {
		mode.remove(p);
		SetLobby.setLobby(p);
	}

	// set to admin mode
	public static void setAdmin(Player p) {
		mode.remove(p);
		mode.put(p, Mode.ADMIN);
		CoreMain.getPlayers().remove(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	// set to troll mode
	public static void setTroll(Player p) {
		mode.remove(p);
		mode.put(p, Mode.TROLL);
		CoreMain.getPlayers().remove(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	// check if normal mode
	public static boolean isNormal(Player p) {
		return !mode.containsKey(p);
	}

	// check if admin mode
	public static boolean isAdmin(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.ADMIN);
		}
		return false;
	}

	// check if troll mode
	public static boolean isTroll(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.TROLL);
		}
		return false;
	}

	// check if the plugin is available
	public static boolean pluginAvailable(String pl) {
		return Bukkit.getServer().getPluginManager().getPlugin(pl) != null;
	}
}
