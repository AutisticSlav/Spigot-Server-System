package net.virushd.creative.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.core.main.SaveUtils;
import net.virushd.creative.main.FileManager;

public class Creative {
	
	public static void SetScoreboard(Player p) {
		p.setScoreboard(SaveUtils.GetScoreboardFromFile(FileManager.sco_creative, "Creative", p));
	}
}