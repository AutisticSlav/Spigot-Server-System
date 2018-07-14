package net.virushd.creative.events;

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

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();

		int MaxPlayers = FileManager.config.getInt("MaxPlayers");
		String NoJoinPerm = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NoJoinPerm"), p);
		String Full = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Full"), p);

		// if the player makes a right click
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = e.getClickedBlock();

			// if the block is a sign
			if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) block.getState();

				// if the player is in the lobby
				if (CoreMain.getPlayers().contains(p)) {

					// if it's the correct sign
					for (int i = 0; i < 4; i++) {
						if (!ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.creativeSign(FileManager.config.getString("Sign.Lines." + (i)))))) {
							return;
						}
					}

					// if the player has permissions
					if (p.hasPermission("virushd.creative.sign.click") || p.hasPermission("*")) {

						// if the game isn't full
						if (CreativeMain.getPlayers().size() < MaxPlayers) {

							// now the player can finally join
							Bukkit.getServer().getScheduler().runTaskLater(CreativeMain.main, () -> {
								SetCreative.setCreative(p);
								for (int i = 0; i < 4; i++) {
									sign.setLine(i, PlaceHolder.creativeSign(FileManager.config.getString("Sign.Lines." + (i))));
								}

								// update the sign
								if (!Updater.updateSigns.contains(sign)) {
									Updater.updateSigns.add(sign);
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

		// if the player wrote [Creative]
		if (e.getLine(0).equals("[Creative]")) {

			// if the player is admin mode
			if (CoreMain.isAdmin(p)) {

				// if the player has permissions
				if (p.hasPermission("virushd.creative.sign.create") || p.hasPermission("*")) {
					for (int i = 0; i < 4; i++) {

						// make the sign correct
						sign.setLine(i, PlaceHolder.creativeSign(FileManager.config.getString("Sign.Lines." + (i))));

						// update the sign
						Updater.updateSigns.add(sign);
					}
				}
			}
		}
	}
}
