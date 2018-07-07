package net.virushd.multiarena.main;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.title.title.Title;

public class SetMultiArena {

	public static void setMultiArena(Player p, int id) {

		Arena theArena = ArenaManager.getArenaByID(id);
		String MultiArenaMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.MultiArena"), p);
		String JoinTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String TabTitleHeader = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Header"), p);
		String TabTitleFooter = PlaceHolder.WithPlayer(FileManager.messages.getString("TabTitle.Footer"), p);
		String JoinMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Message"), p);

		if (theArena != null && theArena.isComplete()) {
			Location Lobby = theArena.getLobby();

			if (!theArena.players.contains(p)) {
				theArena.players.add(p);
			}

			// debug
			if (CoreMain.debug()) {
				MultiArenaMain.main.getLogger().info("DEBUG: " + p.getName() + " joined the arena " + theArena.getName());
			}

			p.teleport(Lobby);
			p.setGameMode(GameMode.ADVENTURE);

			// Title
			Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

			// TabTitle
			Title.sendTabTitle(p, TabTitleHeader, TabTitleFooter);

			// Scoreboard
			net.virushd.core.scoreboards.Lobby.SetScoreboard(p);

			p.sendMessage(MultiArenaMessage);

			for (Player players : theArena.players) {
				if (!players.equals(p)) {
					players.sendMessage(JoinMessage);
				}
			}
		}
	}
}
