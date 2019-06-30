package net.virushd.core.api;

import java.util.List;

import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.SaveUtils;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import net.virushd.core.events.ItemClickEvent;

public class Utils {

	// get some items form the config
	public static ItemStack getHideItem(Player p) {
		ItemStack item = SaveUtils.getItemFromFile(FileManager.itm_cosmetics, "Hide");
		String HideModeOn = PlaceHolder.normal(FileManager.itm_hide.getString("HideMode.on"));
		String HideModeOff = PlaceHolder.normal(FileManager.itm_hide.getString("HideMode.off"));
		ItemMeta meta = item.getItemMeta();
		String HideDisplayName = meta.getDisplayName();

		if (ItemClickEvent.hideMode.contains(p)) {
			meta.setDisplayName(HideDisplayName.replace("{HideMode}", HideModeOn));
		} else {
			meta.setDisplayName(HideDisplayName.replace("{HideMode}", HideModeOff));
		}

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getTeleporterItem() {
		return SaveUtils.getItemFromFile(FileManager.itm_teleporter, "Teleporter");
	}

	public static ItemStack getCosmeticsItem() {
		return SaveUtils.getItemFromFile(FileManager.itm_cosmetics, "Cosmetics");
	}

	/**
	 * copied from: https://bukkit.org/threads/spawn-firework.118019/
	 */
	public static void spawnFirework(Location location, Color color, Type type, int power) {

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

	// get the players rank via permissions
	public static String getRank(Player p) {
		List<String> DisplayNames = FileManager.ranks.getStringList("Ranks.DisplayNames");
		List<String> Permissions = FileManager.ranks.getStringList("Ranks.Permissions");

		if (DisplayNames.size() != Permissions.size()) {
			System.err.println("Error in rank.yml!");
			return "null";
		} else {
			for (int i = 0; i < DisplayNames.size(); i++) {
				if (p.hasPermission(Permissions.get(i))) {
					return PlaceHolder.normal(DisplayNames.get(i));
				}
			}
		}

		return "null";
	}

	// get the area (2D) between two locations
	public static double locationArea(Location pos1, Location pos2) {
		double x = Math.abs(pos2.getX() - pos1.getX());
		double z = Math.abs(pos2.getZ() - pos1.getZ());
		return x * z;
	}

	// teleport a player to a normalized location
	public static void smoothTeleport(Player p, Location l, GameMode gamemode) {
		p.teleport(normalizeLocation(l));
		Bukkit.getServer().getScheduler().runTaskLater(CoreMain.main, () -> {
			p.setGameMode(gamemode);
		}, 3L);
	}

	// normalize a location (center the player and round the yaw and pitch)
	private static Location normalizeLocation(Location l) {
		return new Location(
				l.getWorld(),
				(double) ((int) l.getX()) + 0.5,
				(double) ((int) l.getY()) + 0.3,
				(double) ((int) l.getZ()) + 0.5,
				roundDegreesToQuarter(l.getYaw()),
				roundDegreesToQuarter(l.getPitch())
		);
	}

	// round the yaw and pitch
	private static int roundDegreesToQuarter(float d) {
		return (int) (((float) Math.round(d / 360 * 4)) / 4 * 360);
	}
}
