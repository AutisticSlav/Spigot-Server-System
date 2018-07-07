package net.virushd.multiarena.main;

import java.util.ArrayList;
import java.util.HashMap;

import net.virushd.multiarena.arena.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;

import net.virushd.core.main.PlaceHolder;

public class Updater {
	
	/*
	 * SignUpdater
	 */
	public static ArrayList<Sign> UpdateSigns = new ArrayList<>();
	public static void SignUpdater () {
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MultiArenaMain.main, new Runnable() {
			
			@Override
			public void run() {
				for (Sign sign : UpdateSigns) {
					for (int i = 0; i < 4; i++) {
						sign.setLine(i, PlaceHolder.MultiarenaSign(FileManager.config.getString("Sign.Lines." + (i + 1)), ArenaManager.getArenaBySign(sign).getID()));
						sign.update();
					}
				}
			}
		}, 5L, 5L);
	}
}
