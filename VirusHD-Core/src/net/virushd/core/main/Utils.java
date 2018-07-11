package net.virushd.core.main;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import net.virushd.core.events.ItemClickEvent;

public class Utils {

	@Deprecated
	public static boolean ItemEquals(ItemStack a, ItemStack b) {
		if (a != null && b != null) {
			if (a.getType().equals(b.getType())) {
				if (a.hasItemMeta() && b.hasItemMeta()) {
					ItemMeta am = a.getItemMeta();
					ItemMeta bm = b.getItemMeta();

					if (am.getDisplayName().equals(bm.getDisplayName())) {
						if (am.hasLore() && bm.hasLore()) {
							if (am.getLore().equals(bm.getLore())) {
								return true;
							} else {
								return false;
							}
						} else if (!am.hasLore() && !bm.hasLore()) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else if (!a.hasItemMeta() && !b.hasItemMeta()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static ItemStack HideItem(Player p) {

		ItemStack item = SaveUtils.GetItemFromFile(FileManager.itm_cosmetics, "Hide");
		String HideModeOn = PlaceHolder.Normal(FileManager.itm_hide.getString("HideMode.on"));
		String HideModeOff = PlaceHolder.Normal(FileManager.itm_hide.getString("HideMode.off"));
		ItemMeta meta = item.getItemMeta();
		String HideDisplayName = meta.getDisplayName();
		
		if (ItemClickEvent.HideMode.contains(p)) {
			meta.setDisplayName(HideDisplayName.replace("{HideMode}", HideModeOn));
		} else {
			meta.setDisplayName(HideDisplayName.replace("{HideMode}", HideModeOff));
		}
		
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack TeleporterItem() {
		return SaveUtils.GetItemFromFile(FileManager.itm_teleporter, "Teleporter");
	}
	
	public static ItemStack CosmeticsItem() {
		return SaveUtils.GetItemFromFile(FileManager.itm_cosmetics, "Cosmetics");
	}
	
	/**
	 * copied from: https://bukkit.org/threads/spawn-firework.118019/
	 */
	public static void SpawnFirework (Location location, Color color, Type type, int power) {

        // Spawn the Firework, get the FireworkMeta.
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
       
        // Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(color).withFade(color).with(type).trail(true).build();
       
        // Then apply the effect to the meta
        fwm.addEffect(effect);
       
        // Generate some random power and set it
        fwm.setPower(power);
       
        // Then apply this to our rocket
        fw.setFireworkMeta(fwm);  
	}
	
	public static String GetRank(Player p) {
		List<String> DisplayNames = FileManager.ranks.getStringList("Ranks.DisplayNames");
		List<String> Permissions = FileManager.ranks.getStringList("Ranks.Permissions");
		
		if (DisplayNames.size() != Permissions.size()) {
			System.err.println("Error in rank.yml!");
			return "null";
		} else {
			for (int i = 0; i < DisplayNames.size(); i++) {
				if (p.hasPermission(Permissions.get(i))) {
					return PlaceHolder.Normal(DisplayNames.get(i));
				}
			}
		}
		
		return "null";
	}
	
	public static double LocationDifference(Location pos1, Location pos2) {
		double x = (pos2.getX() - pos1.getX());
		double z = (pos2.getZ() - pos1.getZ());
		return Math.abs(x * z);
	}

	public static void SmoothTeleport(Player p, Location l) {
		p.teleport(LocationToSpawn(l));
	}

	private static Location LocationToSpawn(Location l) {
		return new Location(
			l.getWorld(),
			(double) ((int) l.getX()) + 0.5,
			(double) ((int) l.getY()) + 0.3,
			(double) ((int) l.getZ()) + 0.5,
			RoundDegreesToQuarter(l.getYaw()),
			RoundDegreesToQuarter(l.getPitch())
		);
	}

	private static int RoundDegreesToQuarter(float d) {
		return (int) (((float) Math.round(d / 360 * 4)) / 4 * 360);
	}
}
