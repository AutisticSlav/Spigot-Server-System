package net.virushd.core.events;

import net.virushd.core.main.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.virushd.title.title.Title;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();

		String JoinTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Title"), p);
		String JoinSubTitle = PlaceHolder.withPlayer(FileManager.messages.getString("Join.SubTitle"), p);
		String JoinMessage = PlaceHolder.withPlayer(FileManager.messages.getString("Join.Message"), p);

		// debug
		if (CoreMain.debug()) {
			CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " joined the game.");
		}

		// join the lobby
		PlayerManager.join(p);

		// some titles
		Title.sendTitle(p, 10, 40, 10, JoinTitle, JoinSubTitle);

		// diable the default message
		e.setJoinMessage(null);

		// with a small delay it's smoother
		Bukkit.getServer().getScheduler().runTaskLater(CoreMain.main, () -> {

			// some fireworks
			Utils.spawnFirework(p.getLocation().add(0, 1, 0), Color.RED, Type.STAR, 1);
			Utils.spawnFirework(p.getLocation().add(0, 1, 0), Color.BLACK, Type.BALL_LARGE, 1);
			Utils.spawnFirework(p.getLocation().add(0, 1, 0), Color.BLACK, Type.BURST, 1);
			Utils.spawnFirework(p.getLocation().add(0, 1, 0), Color.RED, Type.BALL, 1);

			// send the player the server modt
			p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Modt"), p));

			// spawn message and sound
			for (Player players : PlayerManager.getPlayers()) {
				if (!players.equals(p)) {
					players.sendMessage(JoinMessage);
					players.playSound(players.getLocation(), Sound.ANVIL_LAND, 1, 1);
				}
			}
		}, 10L);
	}
}
