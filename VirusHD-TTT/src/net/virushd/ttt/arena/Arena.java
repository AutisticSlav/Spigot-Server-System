package net.virushd.ttt.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.virushd.core.main.SetLobby;
import net.virushd.ttt.main.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

@SerializableAs("Arena")
public class Arena {

	private int id;
	private String name;
	private Location lobby;
	private ArrayList<Location> spawns = new ArrayList<>();
	private TimeManager timeManager = new TimeManager(this);
	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<Player> spectators = new ArrayList<>();
	private GameState gameState = GameState.LOBBY;

	public Arena(int id, String name) {
		this.id = id;
		this.name = name;
	}

	// name & id
	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	// spawns & lobby
	public ArrayList<Location> getSpawns() {
		return spawns;
	}

	public void addSpawn(Location spawn) {
		spawns.add(spawn);
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public void deleteSpawns() {
		spawns = new ArrayList<>();
		lobby = null;
	}

	// players & spectators
	public void addPlayer(Player p) {
		players.add(p);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addSpectator(Player p) {
		players.remove(p);
		spectators.add(p);
	}

	public ArrayList<Player> getSpectators() {
		return spectators;
	}

	public void kickPlayers() {
		for (Player p : players) {
			SetLobby.setLobby(p);
			players.remove(p);
		}
		for (Player p : spectators) {
			SetLobby.setLobby(p);
			players.remove(p);
		}
	}

	// gamestate
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState state) {
		gameState = state;
	}

	// check if complete
	public boolean isComplete() {
		return (spawns.size() == FileManager.config.getInt("MaxPlayers") && lobby != null);
	}

	// start the arena
	public void start() {
		if (isComplete()) {
			this.timeManager.start();
		}
	}
}
