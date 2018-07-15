package net.virushd.creative.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.core.api.SaveUtils;
import net.virushd.creative.main.FileManager;

public class Creative {

	public static void setScoreboard(Player p) {

		// load the scoreboard from the file
		p.setScoreboard(SaveUtils.getScoreboardFromFile(FileManager.sco_creative, "Creative", p));
	}
}