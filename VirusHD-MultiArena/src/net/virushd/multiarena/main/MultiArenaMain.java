package net.virushd.multiarena.main;

import net.virushd.multiarena.arena.Arena;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.commands.MultiArena;
import net.virushd.multiarena.events.SignEvent;

import net.virushd.core.main.PlaceHolder;

public class MultiArenaMain extends JavaPlugin {
	
	public static MultiArenaMain main;
	
	public void onEnable() {
		
		// instance
		main = this;
		
		// load arenas
		ArenaManager.loadArenas();
		for(Arena arena : ArenaManager.getCompletedArenas()) {
			arena.start();
		}
		
		// files
		FileManager.Manager();
		
		// commands
		getCommand("mutliarena").setExecutor(new MultiArena());

		// events
		getServer().getPluginManager().registerEvents(new SignEvent(), this);
		
		// updater
		Updater.SignUpdater();
		
		// load message
		getLogger().info("Plugin enabled!");
	}
	
	public void onDisable() {

		// anti sign bug
		for (Sign sign : Updater.UpdateSigns) {
			for (int i = 0; i < 4; i++) {
				sign.setLine(i, PlaceHolder.MultiarenaSign(FileManager.config.getString("Sign.Lines." + (i + 1)).replace("{Players}", "" + 0), ArenaManager.getArenaBySign(sign).getID()));
				sign.update();
			}
		}
	}
}
