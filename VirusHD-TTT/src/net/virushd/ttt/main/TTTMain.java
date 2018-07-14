package net.virushd.ttt.main;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.commands.TTT;
import net.virushd.ttt.events.*;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.ttt.arena.ArenaManager;

import net.virushd.core.main.PlaceHolder;

public class TTTMain extends JavaPlugin {

	public static TTTMain main;

	public void onEnable() {

		// instance
		main = this;

		// files
		FileManager.manager();

		// load arenas
		ArenaManager.loadArenas();
		for (Arena arena : ArenaManager.getCompletedArenas()) {
			arena.start();
		}

		// commands
		getCommand("ttt").setExecutor(new TTT());

		// events
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		getServer().getPluginManager().registerEvents(new ChatEvent(), this);
		getServer().getPluginManager().registerEvents(new CommandEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);

		// updater
		Updater.signUpdater();
		Updater.scoreboardUpdater();
		Updater.playerVisibility();

		// load message
		getLogger().info("Plugin enabled!");
	}

	public void onDisable() {

		// anti sign bug
		for (Sign sign : Updater.updateSigns.keySet()) {
			for (int i = 0; i < 4; i++) {
				try {
					sign.setLine(i, PlaceHolder.tttSign(FileManager.config.getString("Sign.Lines." + (i)).replace("{Players}", "" + 0), Updater.updateSigns.get(sign)));
					sign.update();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
