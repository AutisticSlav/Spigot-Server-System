package net.virushd.citybuild.main;

import org.bukkit.entity.Player;

import net.virushd.core.main.SaveUtils;

@Deprecated
public class GetWorld {
	
	public static String World (Player p) {
		
		org.bukkit.World CityBuild = SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.CityBuild").getWorld();
		org.bukkit.World Nether = SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Nether").getWorld();
		org.bukkit.World Farmwelt = SaveUtils.GetLocationFromFile(FileManager.config, "Spawns.Farmworld").getWorld();
		
		if (p.getWorld().equals(CityBuild)) {
			return "CityBuild";
		} else if (p.getWorld().equals(Nether)) {
			return "Nether";
		} else if (p.getWorld().equals(Farmwelt)) {
			return "Farmwelt";
		} else {
			return "World Error";
		}
	}
}
