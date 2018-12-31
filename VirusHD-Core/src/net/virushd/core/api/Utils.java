package net.virushd.core.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.virushd.core.api.ConfigFile.FileType;
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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

@SuppressWarnings("ConstantConditions")
public class Utils {

	// get some items form the config
	public static ItemStack getHideItem(Player p) {
		ConfigFile file = FileManager.getFile("hide", FileType.ITEMS);
		ItemStack item = Serializer.deserializeItem(Serializer.getMap(file, "Hide"));
		String HideModeOn = PlaceHolder.normal(file.getConfig().getString("HideMode.on"));
		String HideModeOff = PlaceHolder.normal(file.getConfig().getString("HideMode.off"));
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
		return Serializer.deserializeItem(Serializer.getMap(FileManager.getFile("teleporter", FileType.ITEMS), "Teleporter"));
	}

	public static ItemStack getCosmeticsItem() {
		return Serializer.deserializeItem(Serializer.getMap(FileManager.getFile("cosmetics", FileType.ITEMS), "Cosmetics"));
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
		ConfigFile ranks = FileManager.getFile("ranks", FileType.NORMAL);
		List<String> DisplayNames = ranks.getConfig().getStringList("Ranks.DisplayNames");
		List<String> Permissions = ranks.getConfig().getStringList("Ranks.Permissions");

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

	// get the distance between two locations
	public static double locationDifference(Location pos1, Location pos2) {
		double x = (pos2.getX() - pos1.getX());
		double z = (pos2.getZ() - pos1.getZ());
		return Math.abs(x * z);
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

	// simplier scoreboards
	public static class SimpleScoreboard {

		private String displayName;
		private List<String> lines;

		public SimpleScoreboard(String displayName, List<String> lines) {
			this.displayName = displayName;
			this.lines = lines;
		}

		public void setScoreboard(Player p) {
			Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective o = s.registerNewObjective("AAA", "BBB");
			o.setDisplayName(PlaceHolder.withPlayer(displayName, p));
			o.setDisplaySlot(DisplaySlot.SIDEBAR);

			List<String> lines = new ArrayList<>(this.lines);

			// fix a scoreboard space bug
			String spaces = "";
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).equals("{Space}")) {
					lines.set(i, spaces);
					spaces += " ";
				}
			}

			for (int i = 0; i < lines.size(); i++) {
				Score sc = o.getScore(PlaceHolder.withPlayer(lines.get(i), p));
				sc.setScore(lines.size() - i);
			}

			p.setScoreboard(s);
		}

		public String getDisplayName() {
			return displayName;
		}

		public List<String> getLines() {
			return lines;
		}

		public static Map<String, Object> serialize(SimpleScoreboard scoreboard) {
			Map<String, Object> map = new TreeMap<>();
			map.put("DisplayName", scoreboard.getDisplayName());
			map.put("Lines", scoreboard.getLines());
			return map;
		}

		@SuppressWarnings("unchecked")
		public static SimpleScoreboard deserialize(Map<String, Object> map) {
			return new SimpleScoreboard(
					(String) map.get("DisplayName"),
					(List<String>) map.get("Lines")
			);
		}
	}
}
