package net.virushd.multiarena.arena;

import java.util.ArrayList;

import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.SetLobby;
import net.virushd.multiarena.main.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

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
			arena.setLobby(SaveUtils.GetSpawnLocationFromFile(FileManager.arenas, id + ".Lobby"));
		}
		if (FileManager.arenas.contains(id + ".Spawns")) {
			for (int i = 0; i < MaxPlayers; i++) {
				if (FileManager.arenas.contains(id + ".Spawns." + i)) {
					arena.addSpawn(SaveUtils.GetSpawnLocationFromFile(FileManager.arenas, id + ".Spawns." + i));
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

	public static Arena getArenaBySign(Sign sign) {
		for (Arena arena : arenas) {
			ArrayList<Boolean> LinesCorrect = new ArrayList<>();
			for (int i = 0; i < 4; i++) {
				if (ChatColor.stripColor(sign.getLine(i)).equals(ChatColor.stripColor(PlaceHolder.MultiarenaSign(FileManager.config.getString("Sign.Lines." + (i + 1)), arena.getID())))) {
					LinesCorrect.set(i, true);
				}
			}
			if (LinesCorrect.get(1) && LinesCorrect.get(2) && LinesCorrect.get(3) && LinesCorrect.get(4)) {
				return arena;
			}
		}
		return null;
	}

	public static Arena createArena(String name) {
		int id = getFreeID();
		if (id != 333) {
			FileManager.arenas.set(id + ".Name", name);
			FileManager.SaveArenasFile();
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
		for (Player p : arena.players) {
			SetLobby.setLobby(p);
		}
		arenas.remove(arena);
		FileManager.arenas.set("" + id, null);
		FileManager.SaveArenasFile();
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
		FileManager.SaveArenasFile();
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

/*	public static void LoadArenas() {
		for (int i = 1; i <= 10; i++) {
			int id = i;
			if (FileManager.arenas.contains("" + id)) {
				if (ArenaIsComplete(id)) {
					LoadArena(id);
				}
			}
		}
	}
	
	public static void LoadArena(int id) {
		if (ArenaIsComplete(id)) {

			// try to remove the old arena
			try {
				arenas.remove(GetArenaById(id));
			} catch (Exception e) {

			}

			// load the arena
			Arena arena = new Arena(id, FileManager.arenas.getString(id + ".Name"));
			arena.SetLobby(SaveUtils.GetSpawnLocationFromFile(FileManager.arenas, id + ".Lobby"));
			int MaxPlayers = FileManager.config.getInt("MaxPlayers");
			ArrayList<Location> spawns = new ArrayList<>();
			for (int i = 1; i <= MaxPlayers; i++) { // das mit "i = 1" und "<=" so lassen VirusHD!
				spawns.add(SaveUtils.GetSpawnLocationFromFile(FileManager.arenas, id + ".Spawns." + i));
				arena.SetSpawns(spawns);
			}
			arenas.add(arena);
		}
	}

	public static Arena GetArenaById(int id) {
		for (Arena TheArenas:arenas) {
			if (TheArenas.GetId() == id) {
				return TheArenas;
			}
		}
		
		// hier könnte es eine NullPointerException geben
		return null;
	}
	
	public static void CreateArena(String name) {
		for (int i = 1; i <= 10; i++) { // das mit "i = 1" und "<=" so lassen VirusHD!
			int id = i;
			if (!FileManager.arenas.contains("" + id)) {
				FileManager.arenas.set(id + ".Name", name);
				FileManager.SaveArenasFile();
				break;
			}
		}
	}
	
	public static void DeleteArena(int id) {
		arenas.remove(GetArenaById(id));
		FileManager.arenas.set("" + id, null);
	}
	
	public static boolean ArenaIsComplete(int id) {
		if (FileManager.arenas.contains("" + id)) {
			if (FileManager.arenas.getString(id + ".Name") == null) {
				return false;
			} else if (FileManager.arenas.get(id + ".Lobby") == null) {
				return false;
			} else if (FileManager.arenas.get(id + ".Spawns") == null) {
				return false;
			} else {
				int MaxPlayers = FileManager.config.getInt("MaxPlayers");
				for (int i = 1; i <= MaxPlayers; i++) { // das mit "i = 1" und "<=" so lassen VirusHD!
					if (FileManager.arenas.get(id + ".Spawns") == null) {
						return false;
					}
				}
				return true;
			}
		} else {
			return false;
		}
	}*/
}