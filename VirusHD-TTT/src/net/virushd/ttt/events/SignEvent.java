package net.virushd.ttt.events;

import net.md_5.bungee.api.ChatColor;
import net.virushd.ttt.main.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import net.virushd.core.main.PlaceHolder;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import net.virushd.ttt.main.FileManager;
import net.virushd.ttt.main.TTTMain;
import net.virushd.ttt.main.Updater;

import java.util.ArrayList;

public class SignEvent implements Listener {

	private static Arena getArenaBySign(Sign sign) {
		for (Arena arena : ArenaManager.getArenas()) {
			ArrayList<Boolean> LinesCorrect = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				LinesCorrect.add(ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.tttSign(FileManager.config.getString("Sign.Lines." + i), arena.getID()))));
			}
			if (LinesCorrect.get(0) && LinesCorrect.get(1) && LinesCorrect.get(2) && LinesCorrect.get(3)) {
				return arena;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {

		Player p = e.getPlayer();

		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String NoJoinPerm = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NoJoinPerm"), p);
		String Full = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Full"), p);
		String AlreadyStarted = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.AlreadyStarted"), p);

		// if the player makes a right click
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = e.getClickedBlock();

			// if the block is a sign
			if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) block.getState();

				// if the player is in the lobby
				if (net.virushd.core.main.PlayerManager.getPlayers().contains(p)) {

					Arena arena = getArenaBySign(sign);

					// if it's the correct sign and the arena is complete
					if (arena == null || !arena.isComplete()) {
						Updater.updateSigns.remove(sign);
						return;
					}

					// if the player has permissions
					if (p.hasPermission("virushd.ttt.sign.click") || p.hasPermission("*")) {

						// if the game hasn't started yet
						if (arena.getGameState().equals(GameState.LOBBY)) {

							// if the game isn't full
							if (arena.getPlayers().size() < MaxPlayers) {

								// now the player can finally join
								Bukkit.getServer().getScheduler().runTaskLater(TTTMain.main, () -> {
									PlayerManager.join(p, arena.getID());
									for (int i = 0; i < 4; i++) {
										sign.setLine(i, PlaceHolder.tttSign(FileManager.config.getString("Sign.Lines." + i), arena.getID()));
									}

									// update the sign
									Updater.updateSigns.put(sign, arena.getID());
								}, 5L);
							} else {
								p.sendMessage(Full);
							}
						} else {
							p.sendMessage(AlreadyStarted);
						}
					} else {
						p.sendMessage(NoJoinPerm);
					}
				}
			}
		}
	}

	@EventHandler
	public void onSignChange (SignChangeEvent e) {

		Player p = e.getPlayer();

		Block block = e.getBlock();
		Sign sign = (Sign) block.getState();

		for (Arena arena : ArenaManager.getCompletedArenas()) {

			// if the player wrote [TTT] {ID}
			if (e.getLine(0).equals("[TTT] " + arena.getID())) {

				// if the player is admin mode
				if (net.virushd.core.main.PlayerManager.isAdmin(p)) {

					// if the player has permissions
					if (p.hasPermission("virushd.ttt.sign.create") || p.hasPermission("*")) {
						for (int i = 0; i < 4; i++) {

							// make the sign correct
							sign.setLine(i, PlaceHolder.tttSign(FileManager.config.getString("Sign.Lines." + i), arena.getID()));

							// update the sign
							Updater.updateSigns.put(sign, arena.getID());
						}
					}
				}
			}
		}
	}
}