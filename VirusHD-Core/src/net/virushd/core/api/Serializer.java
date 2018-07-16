package net.virushd.core.api;

import net.virushd.inventory.main.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class Serializer {

	// get maps from a file
	public static Map<String, Object> getMap(ConfigFile file, String path) {
		Object o = file.getConfig().get(path);
		if (o instanceof MemorySection) {
			return ((MemorySection) o).getValues(true);
		} else if (o instanceof Map) {
			return (Map<String, Object>) o;
		} else {
			return null;
		}
	}

	// locations

	public static Map<String, Object> serializeLocation(Location location) {
		Map<String, Object> map = new TreeMap<>();
		map.put("World", location.getWorld().getName());
		map.put("X", location.getX());
		map.put("Y", location.getY());
		map.put("Z", location.getZ());
		map.put("Yaw", (double) location.getYaw());
		map.put("Pitch", (double) location.getPitch());
		return map;
	}

	public static Location deserializeLocation(Map<String, Object> map) {
		return new Location(
				Bukkit.getWorld((String) map.get("World")),
				(double) map.get("X"),
				(double) map.get("Y"),
				(double) map.get("Z"),
				(float) (double) map.get("Yaw"),
				(float) (double) map.get("Pitch")
		);
	}

	// items

	public static Map<String, Object> serializeItem(ItemStack item) {
		Map<String, Object> map = new TreeMap<>();
		map.put("Item", item.getType().toString());
		map.put("DisplayName", PlaceHolder.betterColorCode(item.getItemMeta().getDisplayName()));
		map.put("Description", PlaceHolder.betterColorCode(item.getItemMeta().getLore()));
		return map;
	}

	public static ItemStack deserializeItem(Map<String, Object> map) {
		return InventoryAPI.createItem(
				(String) map.get("DisplayName"),
				(List<String>) map.get("Description"),
				Material.valueOf(map.get("Item").toString()),
				null,
				1
		);
	}
}
