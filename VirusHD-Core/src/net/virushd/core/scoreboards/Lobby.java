package net.virushd.core.scoreboards;

import net.virushd.core.api.ConfigFile;
import net.virushd.core.api.Serializer;
import net.virushd.core.api.Utils;
import net.virushd.core.main.FileManager;
import org.bukkit.entity.Player;

public class Lobby {
	
	@SuppressWarnings("ConstantConditions")
	public static void setScoreboard(Player p) {

		// get the scoreboard from the file and open
		Utils.SimpleScoreboard.deserialize(Serializer.getMap(FileManager.getFile("lobby", ConfigFile.FileType.SCOREBOARDS), "Lobby")).setScoreboard(p);
	}
}
