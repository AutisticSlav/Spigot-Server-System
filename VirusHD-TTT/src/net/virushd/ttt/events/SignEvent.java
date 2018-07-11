package net.virushd.ttt.events;

import net.md_5.bungee.api.ChatColor;
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
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.PlaceHolder;
import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import net.virushd.ttt.arena.GameState;
import net.virushd.ttt.main.FileManager;
import net.virushd.ttt.main.TTTMain;
import net.virushd.ttt.main.SetTTT;
import net.virushd.ttt.main.Updater;

import java.util.ArrayList;

public class SignEvent implements Listener {

	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String NoJoinPerm = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoJoinPerm"), p);
		String Full = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Full"), p);
		String AlreadyStarted = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AlreadyStarted"), p);
		
		// wenn der Spieler rechtsklickt
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = e.getClickedBlock();

			// wenn der block ein schild ist
			if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) block.getState();

				// wenn der spieler auch wirklich in der Lobby ist
				if (CoreMain.players.contains(p)) {

					Arena arena = getArenaBySign(sign);

					if (arena == null || !arena.isComplete()) {
						Updater.UpdateSigns.remove(sign);
						return;
					}

					// wenn der spieler die permission hat
					if (p.hasPermission("virushd.ttt.sign.click") || p.hasPermission("*")) {
						
						// wenn das spiel nicht schon begonnen hat
						if (arena.getGameState().equals(GameState.LOBBY)) {
						
							// wenn es noch platz hat
							if (arena.getPlayers().size() < MaxPlayers) {
								// so jetzt kann der spieler endlich joinen
								Bukkit.getServer().getScheduler().runTaskLater(TTTMain.main, () -> {

									SetTTT.setTTT(p, arena.getID());
									for (int i = 0; i < 4; i++) {
										sign.setLine(i, PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID()));
									}

									// das schild updaten
									Updater.UpdateSigns.put(sign, arena.getID());
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
			
			// wenn man [TTT] {ID} geschrieben hat
			if (e.getLine(0).equals("[TTT] " + arena.getID())) {
				// wenn der Spieler im Admin modus ist
				if (CoreMain.isAdmin(p)) {
					
					// wenn er auch noch das recht dazu hat
					if (p.hasPermission("virushd.ttt.sign.create") || p.hasPermission("*")) {
						for (int i = 0; i < 4; i++) {
							
							// das schild richtig machen
							sign.setLine(i, PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID()));
							
							// das schild updaten
							Updater.UpdateSigns.put(sign, arena.getID());
						}
					}
				}
			}
		}
	}

	private static Arena getArenaBySign(Sign sign) {
		for (Arena arena : ArenaManager.getArenas()) {
			ArrayList<Boolean> LinesCorrect = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				/*System.out.println("");
				System.out.println("----------------------------");
				System.out.println("i: " + i);
				System.out.println("Real Line: " + ChatColor.stripColor(sign.getLine(i)));
				System.out.println("Othe Line: " + ChatColor.stripColor(PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID())));
				System.out.println("Equal: " + (ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID())))));
				System.out.println("----------------------------");
				System.out.println("");*/
				LinesCorrect.add(ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.TTTSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID()))));
			}
			if (LinesCorrect.get(0) && LinesCorrect.get(1) && LinesCorrect.get(2) && LinesCorrect.get(3)) {
				return arena;
			}
		}
		return null;
	}
}