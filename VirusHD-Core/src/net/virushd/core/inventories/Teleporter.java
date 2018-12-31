package net.virushd.core.inventories;

import net.virushd.core.api.*;
import net.virushd.core.main.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

public class Teleporter {

	@SuppressWarnings("ConstantConditions")
	public static void open(Player p) {

		ConfigFile teleporter = FileManager.getFile("teleporter", ConfigFile.FileType.INVENTORIES);
		String InventoryDisplayName = PlaceHolder.normal(teleporter.getConfig().getString("Inventory.DisplayName"));
		ItemStack SpawnItem = Serializer.deserializeItem(Serializer.getMap(teleporter, "Items.Spawn"));
		int SpawnSlot = teleporter.getConfig().getInt("Items.Spawn.Slot");

		Inventory inv = InventoryAPI.createInventory(InventoryDisplayName, InventoryType.CHEST);

		// background items
		ItemStack[] BGItems = new ItemStack[27];
		ItemMeta[] BGItemMeta = new ItemMeta[27];

		for (int i = 0; i < 27; i++) {
			BGItems[i] = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) teleporter.getConfig().getInt("Background." + (i)));
			BGItemMeta[i] = BGItems[i].getItemMeta();
			BGItemMeta[i].setDisplayName(" ");
			BGItems[i].setItemMeta(BGItemMeta[i]);
			inv.setSlot(i, BGItems[i], null);
		}

		// spawn item
		inv.setSlot(SpawnSlot, SpawnItem, new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				Utils.smoothTeleport(p, Serializer.deserializeLocation(Serializer.getMap(FileManager.getFile("config", ConfigFile.FileType.NORMAL), "Spawns.Lobby")), GameMode.ADVENTURE);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			}
		});

		// minigame items
		for (Minigame minigame : CoreMain.getMinigames()) {
			inv.setSlot(teleporter.getConfig().getInt("Items." + minigame.getRealName() + ".Slot"), Serializer.deserializeItem(Serializer.getMap(teleporter, "Items." + minigame.getRealName())), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					Utils.smoothTeleport(p, Serializer.deserializeLocation(Serializer.getMap(FileManager.getFile("locations", ConfigFile.FileType.NORMAL), minigame.getRealName())), GameMode.ADVENTURE);
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
				}
			});
		}

		inv.open(p);
	}
}
