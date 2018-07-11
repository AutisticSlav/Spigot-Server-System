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
import net.virushd.core.events.DamageEvent;
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

	// players hashmap
	public static ArrayList<Player> players = new ArrayList<>();
	private static HashMap<Player, Mode> mode = new HashMap<>();

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.Manager();

		// events
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);
		getServer().getPluginManager().registerEvents(new WorldChangeEvent(), this);
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
		Updater.ScoreboardUpdater();
		Updater.RandomMessages();
		Updater.PlayerVisibility();
		Updater.AntiRespawnBug();
		
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
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("Server Stopped");
		}
	}

	public static boolean debug() {
		return FileManager.config.getBoolean("DebugMode");
	}
	
	public static void setNormal(Player p) {
		mode.remove(p);
		SetLobby.setLobby(p);
	}

	public static void setAdmin(Player p) {
		mode.remove(p);
		mode.put(p, Mode.ADMIN);
		CoreMain.players.remove(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	public static void setTroll(Player p) {
		mode.remove(p);
		mode.put(p, Mode.TROLL);
		CoreMain.players.remove(p);
		p.getInventory().clear();
		p.setGameMode(GameMode.CREATIVE);
		Title.sendTabTitle(p, null, null);
		Title.sendActionBar(p, "");
		NoScoreboard.SetScoreboard(p);
	}

	public static boolean isNormal(Player p) {
		return !mode.containsKey(p);
	}

	public static boolean isAdmin(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.ADMIN);
		}
		return false;
	}

	public static boolean isTroll(Player p) {
		if (mode.containsKey(p)) {
			return mode.get(p).equals(Mode.TROLL);
		}
		return false;
	}

	public static boolean pluginAvailable(String pl) {
		return Bukkit.getServer().getPluginManager().getPlugin(pl) != null;
	}
}
