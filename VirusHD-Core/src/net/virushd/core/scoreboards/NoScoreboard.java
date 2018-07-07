package net.virushd.core.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class NoScoreboard {

	public static void SetScoreboard(Player p) {
		Scoreboard NoScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		p.setScoreboard(NoScoreboard);
	}
}
