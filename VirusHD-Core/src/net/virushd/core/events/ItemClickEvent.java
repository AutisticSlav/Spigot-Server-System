package net.virushd.core.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.virushd.core.inventories.Cosmetics;
import net.virushd.core.inventories.Teleporter;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.Utils;

public class ItemClickEvent implements Listener {
	
	public static ArrayList<Player> HideMode = new ArrayList<Player>();
	
	@EventHandler
	public void onItemRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (CoreMain.players.contains(p)) {
			if (e.getItem() != null) {
				
				/*
				 * Hide Item
				 */
				if (/*Utils.ItemEquals(e.getItem(), Utils.HideItem(p))*/e.getItem().isSimilar(Utils.HideItem(p))) {
					if (p.hasPermission("virushd.core.item.hide") || p.hasPermission("*")) {
						if (HideMode.contains(p)) {
							hideOff(p);
							HideMode.remove(p);
						} else {
							hideOn(p);
							HideMode.add(p);
						}
						p.getInventory().setItem(1, Utils.HideItem(p));
					}
					
				/*
				 * Teleport Item
				 */
				} else if (/*Utils.ItemEquals(e.getItem(), Utils.TeleporterItem())*/e.getItem().isSimilar(Utils.TeleporterItem())){
					if (p.hasPermission("virushd.core.item.teleporter") || p.hasPermission("*")) {
						Teleporter.open(p);
					}
				
				/*
				 * Cosmetics Item
				 */
				} else if (/*Utils.ItemEquals(e.getItem(), Utils.CosmeticsItem())*/e.getItem().isSimilar(Utils.CosmeticsItem())) {
					if (p.hasPermission("virushd.core.item.cosmetics") || p.hasPermission("*")) {
						Cosmetics.open(p);
					}
				}
			}
		}
	}
	
	public static void hideOn (Player p) {
		for (Player players : CoreMain.players) {
			p.hidePlayer(players);
		}
	}
	
	public static void hideOff (Player p) {
		for (Player players : CoreMain.players) {
			p.showPlayer(players);
		}
	}
}
