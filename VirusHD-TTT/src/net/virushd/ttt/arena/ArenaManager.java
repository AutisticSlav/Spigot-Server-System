package net.virushd.ttt.arena;

import java.util.ArrayList;

import net.virushd.core.main.SaveUtils;
import net.virushd.ttt.main.FileManager;
import org.bukkit.Location;

public class ArenaManager {
	
	private static ArrayList<Arena> arenas = new ArrayList<>();

	public static void loadArenas() {
		for (int i = 0; i < 10; i++) {
			if (FileManager.arenas.contains("" + i)) {
				loadArena(i);
			}
		}
	}

	public static Arena loadArena(int id) {

		Arena arena = new Arena(id, FileManager.arenas.getString(id + ".Name"));
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");

		if (FileManager.arenas.contains(id + ".Lobby")) {
			arena.setLobby(SaveUtils.GetLocationFromFile(FileManager.arenas, id + ".Lobby"));
		}
		if (FileManager.arenas.contains(id + ".Spawns")) {
			for (int i = 0; i < MaxPlayers; i++) {
				if (FileManager.arenas.contains(id + ".Spawns." + i)) {
					arena.addSpawn(SaveUtils.GetLocationFromFile(FileManager.arenas, id + ".Spawns." + i));
				}
			}
		}

		arenas.add(arena);
		return arena;
	}

	public static Arena getArenaByID(int id) {
		for (Arena arena : arenas) {
			if (arena.getID() == id) {
				return arena;
			}
		}
		return null;
	}

	public static Arena createArena(String name) {
		int id = getFreeID();
		if (id != 333) {
			FileManager.arenas.set(id + ".Name", name);
			SaveUtils.SaveFile(FileManager.arenasF, FileManager.arenas);
			SaveUtils.SaveFile(FileManager.arenasF, FileManager.arenas);
			return loadArena(id);
		}
		return null;
	}

	private static int getFreeID() {
		for (int i = 0; i < 10; i++) {
			if (!FileManager.arenas.contains("" + i)) {
				return i;
			}
		}
		return 333; // alternative to null
	}

	public static void deleteArena(int id) {
		Arena arena = getArenaByID(id);
		arena.kickPlayers();
		arenas.remove(arena);
		FileManager.arenas.set("" + id, null);
		SaveUtils.SaveFile(FileManager.arenasF, FileManager.arenas);
	}

	public static void saveArena(int id) {
		Arena arena = getArenaByID(id);
		if (arena != null) {
			ArrayList<Location> spawns = arena.getSpawns();
			FileManager.arenas.set(id + ".Spawns", null);
			if (spawns != null) {
				for (int i = 0; i < spawns.size(); i++) {
					SaveUtils.SaveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Spawns." + i, spawns.get(i));
				}
			}
			FileManager.arenas.set(id + ".Lobby", null);
			if (arena.getLobby() != null) {
				SaveUtils.SaveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Lobby", arena.getLobby());
			}
		}
		SaveUtils.SaveFile(FileManager.arenasF, FileManager.arenas);
	}

	public static ArrayList<Arena> getArenas() {
		return arenas;
	}

	public static ArrayList<Arena> getCompletedArenas() {
		ArrayList<Arena> completed = new ArrayList<>();
		for (Arena a : arenas) {
			if (a.isComplete()) {
				completed.add(a);
			}
		}
		return completed;
	}
}