package net.virushd.ttt.main;

import net.virushd.core.main.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.title.title.Title;

public class SetTTT {

	public static void setTTT(Player p, int id) {

		Arena theArena = ArenaManager.getArenaByID(id);
		String TTTMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.TTT"), p);
		String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.withPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

		if (theArena != null && theArena.isComplete()) {
			if (!theArena.getPlayers().contains(p)) {
				theArena.addPlayer(p);
			}

			// debug
			if (CoreMain.debug()) {
				TTTMain.main.getLogger().info("DEBUG: " + p.getName() + " joined the arena " + theArena.getName());
			}

			Utils.smoothTeleport(p, theArena.getLobby());
			p.setGameMode(GameMode.ADVENTURE);

			// Title
			Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

			// TabTitle
			Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

			// Scoreboard
			net.virushd.core.scoreboards.Lobby.setScoreboard(p);

			p.sendMessage(TTTMessage);

			for (Player players : theArena.getPlayers()) {
				if (!players.equals(p)) {
					players.sendMessage(JoinMessage);
				}
			}
		}
	}
}
