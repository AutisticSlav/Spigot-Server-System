package net.virushd.citybuild.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.citybuild.main.FileManager;
import net.virushd.core.main.SaveUtils;

public class CityBuild {

	public static void SetScoreboard(Player p) {
		p.setScoreboard(SaveUtils.GetScoreboardFromFile(FileManager.sco_citybuild, "CityBuild", p));
	}
}