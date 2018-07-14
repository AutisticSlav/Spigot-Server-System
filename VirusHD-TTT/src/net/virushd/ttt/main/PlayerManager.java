package net.virushd.ttt.main;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.Utils;
import net.virushd.title.title.Title;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

	// join
	public static void join(Player p, int id) {

		Arena arena = ArenaManager.getArenaByID(id);
		String TTTMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.TTT"), p);
		String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

		// if the arena is complete
		if (arena != null && arena.isComplete()) {
			if (!arena.getPlayers().contains(p)) {

				// add the player
				arena.addPlayer(p);

				// remove the player from the lobby
				net.virushd.core.main.PlayerManager.leaveQuiet(p);

				// teleport
				Utils.smoothTeleport(p, arena.getLobby(), GameMode.ADVENTURE);

				// Title
				Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

				// TabTitle
				Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

				// Scoreboard
				net.virushd.core.scoreboards.Lobby.setScoreboard(p);

				// send the join message
				p.sendMessage(TTTMessage);
				for (Player players : arena.getPlayers()) {
					if (!players.equals(p)) {
						players.sendMessage(JoinMessage);
					}
				}

				// debug
				if (CoreMain.debug()) {
					TTTMain.main.getLogger().info("DEBUG: " + p.getName() + " joined TTT");
				}
			}
		}
	}

	// leave
	public static void leave(Player p) {

		for (Arena a : ArenaManager.getArenas()) {
			if (a.getPlayers().contains(p)) {

				// remove the player
				a.getPlayers().remove(p);

				// send the quit message
				String QuitMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Quit.Message"), p);
				for (Player players : a.getPlayers()) {
					players.sendMessage(QuitMessage);
				}

				// debug
				if (CoreMain.debug()) {
					TTTMain.main.getLogger().info("DEBUG: " + p.getName() + " left TTT.");
				}
			}
		}
	}
}
