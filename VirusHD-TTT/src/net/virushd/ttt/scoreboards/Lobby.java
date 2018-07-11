package net.virushd.ttt.scoreboards;

import net.virushd.core.main.SaveUtils;
import net.virushd.ttt.main.FileManager;
import org.bukkit.entity.Player;

public class Lobby {
	public static void SetScoreboard(Player p) {
		p.setScoreboard(SaveUtils.GetScoreboardFromFile(FileManager.sco_lobby, "Lobby", p));
	}
}
