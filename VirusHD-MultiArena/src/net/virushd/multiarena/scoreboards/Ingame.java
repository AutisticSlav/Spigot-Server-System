package net.virushd.multiarena.scoreboards;

import net.virushd.core.main.SaveUtils;
import net.virushd.multiarena.main.FileManager;
import org.bukkit.entity.Player;

public class Ingame {
	public static void SetScoreboard(Player p) {
		p.setScoreboard(SaveUtils.GetScoreboardFromFile(FileManager.sco_ingame, "Ingame", p));
	}
}
