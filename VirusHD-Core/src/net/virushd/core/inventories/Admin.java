package net.virushd.core.inventories;

import java.util.Arrays;

import net.virushd.core.api.ConfigFile;
import net.virushd.core.api.Minigame;
import net.virushd.core.api.Serializer;
import net.virushd.core.main.CoreMain;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import net.virushd.core.main.FileManager;
import net.virushd.core.api.SaveUtils;
import net.virushd.inventory.inventory.Inventory;
import net.virushd.inventory.inventory.ItemListener;
import net.virushd.inventory.main.InventoryAPI;

@SuppressWarnings({"ConstantConditions", "ArraysAsListWithZeroOrOneArgument"})
public class Admin {

	public static void open(Player p) {

		Inventory inv = InventoryAPI.createInventory("&cAdmin Menu", InventoryType.CHEST);
		Inventory locations = InventoryAPI.createInventory("&cLocations", InventoryType.CHEST);

		// change the spawn location
		inv.setSlot(0, InventoryAPI.createItem("&cSpawn", Arrays.asList("&7Change the spawn location."), Material.valueOf(FileManager.getFile("teleporter", ConfigFile.FileType.INVENTORIES).getConfig().getString("Items.Spawn.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				FileManager.getFile("config", ConfigFile.FileType.NORMAL).set("Spawns.Lobby", Serializer.serializeLocation(p.getLocation()));
			}
		});

		// change other locations (teleporter)
		inv.setSlot(1, InventoryAPI.createItem("&cTeleporter Locations", Arrays.asList("&7Set the Locations of the Teleporter."), Material.valueOf(FileManager.getFile("teleporter", ConfigFile.FileType.ITEMS).getConfig().getString("Teleporter.Item")), null, 1), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				locations.open(p);
			}
		});

		for (int i = 0; i < CoreMain.getMinigames().size(); i++) {
			Minigame minigame = CoreMain.getMinigames().get(i);
			locations.setSlot(i, InventoryAPI.createItem("&c" + minigame.getRealName(), Arrays.asList("&7Change the " + minigame.getRealName() + " location."), Material.valueOf(FileManager.getFile("teleporter", ConfigFile.FileType.INVENTORIES).getConfig().getString("Items." + minigame.getRealName() + ".Item")), null, 1), new ItemListener() {
				@Override
				public void onItemClick(Player p) {
					FileManager.getFile("locations", ConfigFile.FileType.NORMAL).set(minigame.getRealName(), Serializer.serializeLocation(p.getLocation()));
				}
			});
		}

		// leave the inventory SaveUtils.getItemFromFile(FileManager.itm_inventory, "Leave")
		locations.setSlot(18, Serializer.deserializeItem(Serializer.getMap(FileManager.getFile("inventory", ConfigFile.FileType.ITEMS), "Leave")), new ItemListener() {
			@Override
			public void onItemClick(Player p) {
				inv.open(p);
			}
		});

		inv.open(p);
	}
}
