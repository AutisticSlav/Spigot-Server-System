package net.virushd.ttt.arena;

import java.util.ArrayList;

import net.virushd.core.api.SaveUtils;
import net.virushd.ttt.main.FileManager;
import org.bukkit.Location;

public class ArenaManager {

	private static ArrayList<Arena> arenas = new ArrayList<>();

	// load all arenas
	public static void loadArenas() {
		for (int i = 0; i < 10; i++) {
			if (FileManager.arenas.contains("" + i)) {
				loadArena(i);
			}
		}
	}

	// load an arena by its id
	public static void loadArena(int id) {

		Arena arena = new Arena(id, FileManager.arenas.getString(id + ".Name"));
		int MaxPlayers = FileManager.config.getInt("MaxPlayers");

		if (FileManager.arenas.contains(id + ".Lobby")) {
			arena.setLobby(SaveUtils.getLocationFromFile(FileManager.arenas, id + ".Lobby"));
		}
		if (FileManager.arenas.contains(id + ".Spawns")) {
			for (int i = 0; i < MaxPlayers; i++) {
				if (FileManager.arenas.contains(id + ".Spawns." + i)) {
					arena.addSpawn(SaveUtils.getLocationFromFile(FileManager.arenas, id + ".Spawns." + i));
				}
			}
		}

		arenas.add(arena);
	}

	// get a arena by its id
	public static Arena getArenaByID(int id) {
		for (Arena arena : arenas) {
			if (arena.getID() == id) {
				return arena;
			}
		}
		return null;
	}

	// create an arena with a name
	public static void createArena(String name) {
		Integer id = getFreeID();
		if (id != null) {
			FileManager.arenas.set(id + ".Name", name);
			SaveUtils.saveFile(FileManager.arenasF, FileManager.arenas);
			SaveUtils.saveFile(FileManager.arenasF, FileManager.arenas);
			loadArena(id);
		}
	}

	// get a free id
	private static Integer getFreeID() {
		for (int i = 0; i < 10; i++) {
			if (!FileManager.arenas.contains("" + i)) {
				return i;
			}
		}
		return null;
	}

	// delete an arena by its id
	public static void deleteArena(int id) {
		Arena arena = getArenaByID(id);
		arena.kickPlayers();
		arenas.remove(arena);
		FileManager.arenas.set("" + id, null);
		SaveUtils.saveFile(FileManager.arenasF, FileManager.arenas);
	}

	// save an arena by its id
	public static void saveArena(int id) {
		Arena arena = getArenaByID(id);
		if (arena != null) {
			ArrayList<Location> spawns = arena.getSpawns();
			FileManager.arenas.set(id + ".Spawns", null);
			if (spawns != null) {
				for (int i = 0; i < spawns.size(); i++) {
					SaveUtils.saveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Spawns." + i, spawns.get(i));
				}
			}
			FileManager.arenas.set(id + ".Lobby", null);
			if (arena.getLobby() != null) {
				SaveUtils.saveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Lobby", arena.getLobby());
			}
		}
		SaveUtils.saveFile(FileManager.arenasF, FileManager.arenas);
	}

	// get all arenas
	public static ArrayList<Arena> getArenas() {
		return arenas;
	}

	// get only the completed ones
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