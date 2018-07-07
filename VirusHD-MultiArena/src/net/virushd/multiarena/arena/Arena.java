package net.virushd.multiarena.arena;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {

	private int id;
	private String name;
	private Location lobby;
	private ArrayList<Location> spawns;
	private TimeManager timeManager = new TimeManager(this);

	public ArrayList<Player> players;
	public GameState GameState;

	Arena(int id, String name) {
		this.id = id;
		this.name = name;
	}

	// name & id
	public String getName() { return name; }
	public int getID() { return id; }

	// spawns & lobby
	public ArrayList<Location> getSpawns() { return spawns; }
	public void addSpawn(Location spawn) { spawns.add(spawn); }

	public Location getLobby() { return lobby; }
	public void setLobby(Location lobby) { this.lobby = lobby; }

	public void deleteSpawns() { spawns = new ArrayList<>(); lobby = null; }

	// check if complete
	public boolean isComplete() {
		return true;
	}

	// start the arena
	public void start() {
		if (isComplete()) {
			this.timeManager.start();
		}
	}
}
