package net.virushd.multiarena.events;

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
import net.virushd.multiarena.arena.Arena;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.arena.GameState;
import net.virushd.multiarena.main.FileManager;
import net.virushd.multiarena.main.MultiArenaMain;
import net.virushd.multiarena.main.SetMultiArena;
import net.virushd.multiarena.main.Updater;

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

					Arena theArena = ArenaManager.getArenaBySign(sign);

					if (theArena == null || !theArena.isComplete()) {
						Updater.UpdateSigns.remove(sign);
						return;
					}

					// wenn der spieler die permission hat
					if (p.hasPermission("virushd.multiarena.sign.click") || p.hasPermission("*")) {
						
						// wenn das spiel nicht schon begonnen hat
						if (theArena.GameState.equals(GameState.LOBBY)) {
						
							// wenn es noch platz hat
							if (theArena.players.size() < MaxPlayers) {
								Bukkit.getServer().getScheduler().runTaskLater(MultiArenaMain.main, new Runnable() {
	
									// so jetzt kann der spieler endlich joinen
									@Override
									public void run() {
										
										SetMultiArena.setMultiArena(p, theArena.getID());
										for (int i = 0; i < 4; i++) {
											sign.setLine(i, PlaceHolder.MultiarenaSign(FileManager.config.getString("Sign.Lines." + (i + 1)), theArena.getID()));
										}
										
										// das schild updaten
										if (!Updater.UpdateSigns.contains(sign)) {
											Updater.UpdateSigns.add(sign);
										}
									}
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
			
			// wenn man [MultiArena] {ID} geschrieben hat
			if (e.getLine(0).equals("[MultiArena] " + arena.getID())) {
				// wenn der Spieler im Admin modus ist
				if (CoreMain.isAdmin(p)) {
					
					// wenn er auch noch das recht dazu hat
					if (p.hasPermission("virushd.multiarena.sign.create") || p.hasPermission("*")) {
						for (int i = 0; i < 4; i++) {
							
							// das schild richtig machen
							sign.setLine(i, PlaceHolder.MultiarenaSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID()));
							
							// das schild updaten
							Updater.UpdateSigns.add(sign);
						}
					}
				}
			}
		}
	}
}