package net.virushd.ttt.arena;

import net.virushd.core.api.PlaceHolder;
import net.virushd.core.api.Utils;
import net.virushd.ttt.main.FileManager;
import net.virushd.ttt.main.TTTMain;
import net.virushd.title.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Random;

class TimeManager {

	private Arena arena;
	private BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	private int MinPlayers = FileManager.config.getInt("MinPlayers");
	private int LobbyCountdown;
	private int StartingCountdown;
	private int RestartingCountdown;

	private String LobbyID = "";
	private String PrestartingID = "";
	private String StartingID = "";
	private String GameID = "";
	private String RestartingID = "";

	private Runnable Prestarting;
	private Runnable Lobby;
	private Runnable Starting;
	private Runnable Game;
	private Runnable Restarting;

	public TimeManager(Arena arena) {
		this.arena = arena;
		setup();
	}

	// start the whole time management
	public void start() {
		if (arena.isComplete()) {
			LobbyID = schedule(Lobby, 0, 5L, LobbyID);
			StartingCountdown = FileManager.config.getInt("Countdown.Starting");
			LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			RestartingCountdown = FileManager.config.getInt("Countdown.Restarting");
		}
	}

	// initialize the runnables
	private void setup() {

		String LobbyCountdownMessage = FileManager.messages.getString("Countdown.Lobby.Message");
		String StartingCountdownTitle = FileManager.messages.getString("Countdown.Starting.Title");
		String StartingCountdownMessage = FileManager.messages.getString("Countdown.Starting.Message");
		String StartingCountdownFinal = FileManager.messages.getString("Countdown.Starting.Final");
		String RestartingCountdownMessage = FileManager.messages.getString("Countdown.Restarting.Message");

		Lobby = () -> {
			arena.setGameState(GameState.LOBBY);
			if (arena.getPlayers().size() >= MinPlayers) {
				PrestartingID = schedule(Prestarting, 20L, 20L, PrestartingID);
			} else {
				PrestartingID = unSchedule(PrestartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			}
		};

		Prestarting = () -> {
			if (LobbyCountdown == 0) {
				LobbyID = unSchedule(LobbyID);
				PrestartingID = unSchedule(PrestartingID);
				teleportPlayers();
				StartingID = schedule(Starting, 20L, 20L, StartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.withPlayer(LobbyCountdownMessage, p).replace("{Time}", "" + LobbyCountdown));
				}
				LobbyCountdown--;
			}
		};

		Starting = () -> {
			arena.setGameState(GameState.STARTING);
			if (StartingCountdown == 0) {
				StartingID = unSchedule(StartingID);
				GameID = schedule(Game, 0, 0, GameID);
				StartingCountdown = FileManager.config.getInt("Countdown.Starting");
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.withPlayer(StartingCountdownFinal, p));
				}
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.withPlayer(StartingCountdownMessage, p).replace("{Time}", "" + StartingCountdown));
					if (StartingCountdown <= 3) {
						Title.sendTitle(p, 0, 20, 0, PlaceHolder.withPlayer(StartingCountdownTitle, p).replace("{Time}", "" + StartingCountdown), "");
					}
				}
				StartingCountdown--;
			}
		};

		Game = () -> {
			arena.setGameState(GameState.GAME);
			if (arena.getPlayers().size() == 0) {
				GameID = unSchedule(GameID);
				RestartingID = schedule(Restarting, 0, 20L, RestartingID);
			}
		};

		Restarting = () -> {
			arena.setGameState(GameState.RESTARTING);
			if (RestartingCountdown == 0) {
				RestartingID = unSchedule(RestartingID);
				RestartingCountdown = FileManager.config.getInt("Countdown.Starting");
				arena.kickPlayers();
				arena.start();
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.withPlayer(RestartingCountdownMessage, p).replace("{Time}", "" + RestartingCountdown));
				}
				RestartingCountdown--;
			}
		};
	}

	// teleport all the players to the spawns
	private void teleportPlayers() {
		ArrayList<Location> spawnsLeft = new ArrayList<>(arena.getSpawns());
		for (Player p : arena.getPlayers()) {
			Utils.smoothTeleport(p, spawnsLeft.remove(new Random().nextInt(spawnsLeft.size())), GameMode.ADVENTURE);
		}
	}

	// repeat a runnable
	private String schedule(Runnable task, long start, long update, String ID) {
		if (ID.equals("")) {
			return "" + scheduler.scheduleSyncRepeatingTask(TTTMain.main, task, start, update);
		}
		return ID;
	}

	// cancel it
	private String unSchedule(String ID) {
		if (!ID.equals("")) {
			scheduler.cancelTask(Integer.parseInt(ID));
		}
		return "";
	}
}
