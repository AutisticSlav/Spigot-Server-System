package net.virushd.creative.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import net.virushd.creative.main.CreativeMain;
import net.virushd.creative.main.FileManager;
import net.virushd.creative.main.SetCreative;
import net.virushd.creative.main.Updater;

public class SignEvent implements Listener {
	
	public static ArrayList<Sign> signs = new ArrayList<Sign>();
	
	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String NoJoinPerm = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoJoinPerm"), p);
		String Full = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Full"), p);

		// wenn der Spieler rechtsklickt
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = e.getClickedBlock();
			
			// wenn der block ein schild ist
			if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) block.getState();

				// wenn der spieler auch wirklich in der Lobby ist
				if (CoreMain.players.contains(p)) {
					
					// wenn es auch wirlich des richtige schild ist
					for (int i = 0; i < 4; i++) {
						if (!ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.CreativeSign(FileManager.config.getString("Sign.Lines." + (i + 1)))))) {
							return;
						}
					}
					
					// das schild updaten
					sign.update(true);
					
					// wenn der spieler die permission hat
					if (p.hasPermission("virushd.creative.sign.click") || p.hasPermission("*")) {
						
						// wenn es noch platz hat
						if (CreativeMain.players.size() < MaxPlayers) {
							Bukkit.getServer().getScheduler().runTaskLater(CreativeMain.main, new Runnable() {

								// so jetzt kann der spieler endlich joinen
								@Override
								public void run() {
									SetCreative.setCreative(p);
									for (int i = 0; i < 4; i++) {
										sign.setLine(i, PlaceHolder.CreativeSign(FileManager.config.getString("Sign.Lines." + (i + 1))));
									}
									if (!Updater.UpdateSigns.contains(sign)) {
										Updater.UpdateSigns.add(sign);
									}
								}
							}, 5L);
						} else {
							p.sendMessage(Full);
						}
					} else {
						p.sendMessage(NoJoinPerm);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		
		Player p = e.getPlayer();
		
		Block block = e.getBlock();
		Sign sign = (Sign) block.getState();
		
		// wenn man [Creative] geschrieben hat
		if (e.getLine(0).equals("[Creative]")) {
			
			// wenn der Spieler im Admin modus ist
			if (CoreMain.isAdmin(p)) {
				
				// wenn er auch noch das recht dazu hat
				if (p.hasPermission("virushd.creative.sign.create") || p.hasPermission("*")) {
					for (int i = 0; i < 4; i++) {
						
						// das schild richtig machen
						sign.setLine(i, PlaceHolder.CreativeSign(FileManager.config.getString("Sign.Lines." + (i + 1))));
						
						// das schild updaten
						Updater.UpdateSigns.add(sign);
					}
				}
			}
		}
	}
}
