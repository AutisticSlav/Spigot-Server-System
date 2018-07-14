package net.virushd.citybuild.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.SaveUtils;

public class CityBuild {

	public static void setScoreboard(Player p) {

		// load the scoreboard from the file
		p.setScoreboard(SaveUtils.getScoreboardFromFile(FileManager.sco_citybuild, "CityBuild", p));
	}
}