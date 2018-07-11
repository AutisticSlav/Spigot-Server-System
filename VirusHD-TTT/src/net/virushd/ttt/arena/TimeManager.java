package net.virushd.ttt.arena;

import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.Utils;
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

	public void start() {
		if (arena.isComplete()) {
			LobbyID = schedule(Lobby, 0,5L, LobbyID);
			StartingCountdown = FileManager.config.getInt("Countdown.Starting");
			LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			RestartingCountdown = FileManager.config.getInt("Countdown.Restarting");
		}
	}

	private void setup() {

		String LobbyCountdownMessage = FileManager.messages.getString("Countdown.Lobby.Message");
		String StartingCountdownTitle = FileManager.messages.getString("Countdown.Starting.Title");
		String StartingCountdownMessage = FileManager.messages.getString("Countdown.Starting.Message");
		String StartingCountdownFinal = FileManager.messages.getString("Countdown.Starting.Final");
		String RestartingCountdownMessage = FileManager.messages.getString("Countdown.Restarting.Message");

		Lobby = () -> {
			for (Player p : arena.getPlayers()) {
				if (p.getGameMode() != GameMode.ADVENTURE) {
					p.setGameMode(GameMode.ADVENTURE);
				}
			}
			arena.setGameState(GameState.LOBBY);
			if (arena.getPlayers().size() >= MinPlayers) {
				PrestartingID = schedule(Prestarting, 20L,20L, PrestartingID);
			} else {
				unSchedule(PrestartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			}
		};

		Prestarting = () -> {
			if (LobbyCountdown == 0) {
				unSchedule(LobbyID);
				unSchedule(PrestartingID);
				TeleportPlayers();
				StartingID = schedule(Starting, 20L,20L, StartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.WithPlayer(LobbyCountdownMessage, p).replace("{Time}", "" + LobbyCountdown));
				}
				LobbyCountdown--;
			}
		};

		Starting = () -> {
			arena.setGameState(GameState.STARTING);
			if (StartingCountdown == 0) {
				unSchedule(StartingID);
				GameID = schedule(Game, 0, 0, GameID);
				StartingCountdown = FileManager.config.getInt("Countdown.Starting");
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.WithPlayer(StartingCountdownFinal, p));
				}
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.WithPlayer(StartingCountdownMessage, p).replace("{Time}", "" + StartingCountdown));
					if (StartingCountdown <= 3) {
						Title.sendTitle(p, 0,20,0, PlaceHolder.WithPlayer(StartingCountdownTitle, p).replace("{Time}", "" + StartingCountdown), "");
					}
				}
				StartingCountdown--;
			}
		};

		Game = () -> {
			arena.setGameState(GameState.GAME);
			if (arena.getPlayers().size() == 0) {
				unSchedule(GameID);
				RestartingID = schedule(Restarting, 0, 20L, RestartingID);
			}
		};

		Restarting = () -> {
			arena.setGameState(GameState.RESTARTING);
			if (RestartingCountdown == 0) {
				unSchedule(RestartingID);
				RestartingCountdown = FileManager.config.getInt("Countdown.Starting");
				arena.kickPlayers();
				arena.start();
			} else {
				for (Player p : arena.getPlayers()) {
					p.sendMessage(PlaceHolder.WithPlayer(RestartingCountdownMessage, p).replace("{Time}", "" + RestartingCountdown));
				}
				RestartingCountdown--;
			}
		};
	}

	private void TeleportPlayers() {
		ArrayList<Location> spawnsLeft = new ArrayList<>(arena.getSpawns());
		for (Player p : arena.getPlayers()) {
			Utils.SmoothTeleport(p, spawnsLeft.remove(new Random().nextInt(spawnsLeft.size())));
			p.setGameMode(GameMode.ADVENTURE);
		}
	}

	private String schedule(Runnable task, long start, long update, String ID) {
		if (ID.equals("")) {
			return "" + scheduler.scheduleSyncRepeatingTask(TTTMain.main, task, start, update);
		}
		return ID;
	}

	private void unSchedule(String ID) {
		if (!ID.equals("")) {
			scheduler.cancelTask(Integer.parseInt(ID));
		}
	}
 }
