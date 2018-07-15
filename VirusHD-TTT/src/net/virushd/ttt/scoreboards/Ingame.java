package net.virushd.ttt.scoreboards;

import net.virushd.core.api.SaveUtils;
import net.virushd.ttt.main.FileManager;
import org.bukkit.entity.Player;

public class Ingame {

	public static void setScoreboard(Player p) {

		// load the scoreboard from the file
		p.setScoreboard(SaveUtils.getScoreboardFromFile(FileManager.sco_ingame, "Ingame", p));
	}
}
