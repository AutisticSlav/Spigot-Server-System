package net.virushd.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SetLobby;
import net.virushd.core.main.Utils;
import net.virushd.title.title.Title;

public class JoinEvent implements Listener {

	private CoreMain pl;
	
	public JoinEvent(CoreMain main) {
		this.pl = main;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent e) {		
		
		Player p = e.getPlayer();
		
		// debug
		if (CoreMain.debug()) {
			CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " joined the game.");
		}
		
		if (!CoreMain.players.contains(p)) {
			CoreMain.players.add(p);
		}
		
		String JoinTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String JoinMessage = PlaceHolder.WithPlayer(FileManager.messages.getString("Join.Message"), p);
		
		SetLobby.setLobby(p);
		
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);
		
		e.setJoinMessage(null);
		
		Bukkit.getServer().getScheduler().runTaskLater(pl, new Runnable() {
			@Override
			public void run() {
				Utils.SpawnFirework(p.getLocation().add(0, 1, 0), Color.RED, Type.STAR, 1);
				Utils.SpawnFirework(p.getLocation().add(0, 1, 0), Color.BLACK, Type.BALL_LARGE, 1);
				Utils.SpawnFirework(p.getLocation().add(0, 1, 0), Color.BLACK, Type.BURST, 1);
				Utils.SpawnFirework(p.getLocation().add(0, 1, 0), Color.RED, Type.BALL, 1);
				
				p.sendMessage(PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Modt"), p));
				
				for (Player players : CoreMain.players) {
					if (players.equals(p)) {
						
					} else {
						players.sendMessage(JoinMessage);
						players.playSound(players.getLocation(), Sound.ANVIL_LAND, 1, 1);
					}
				}
			}
		}, 10L);
	}
}
