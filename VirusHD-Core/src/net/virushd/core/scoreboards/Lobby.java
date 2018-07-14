package net.virushd.core.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.SaveUtils;

public class Lobby {
	
	public static void setScoreboard(Player p) {

		// get the scoreboard from the file
		p.setScoreboard(SaveUtils.getScoreboardFromFile(FileManager.sco_lobby, "Lobby", p));
	}
}
