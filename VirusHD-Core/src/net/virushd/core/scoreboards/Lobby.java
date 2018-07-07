package net.virushd.core.scoreboards;

import org.bukkit.entity.Player;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.SaveUtils;

public class Lobby {
	public static void SetScoreboard(Player p) {
		p.setScoreboard(SaveUtils.GetScoreboardFromFile(FileManager.sco_lobby, "Lobby", p));
	}
}
