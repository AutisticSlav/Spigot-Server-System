package net.virushd.multiarena.arena;

import net.virushd.multiarena.main.FileManager;
import net.virushd.multiarena.main.MultiArenaMain;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

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
			LobbyID = schedule(Lobby, 1, LobbyID);
			StartingCountdown = FileManager.config.getInt("Countdown.Starting");
			LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			RestartingCountdown = FileManager.config.getInt("Countdown.Restarting");
		}
	}

	private void setup() {
		Lobby = () -> {
			if (arena.players.size() >= MinPlayers) {
				PrestartingID = schedule(Prestarting, 20, PrestartingID);
			} else {
				unSchedule(PrestartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			}
		};

		Prestarting = () -> {
			if (LobbyCountdown == 0) {
				unSchedule(LobbyID);
				unSchedule(PrestartingID);
				StartingID = schedule(Starting, 20, StartingID);
				LobbyCountdown = FileManager.config.getInt("Countdown.Lobby");
			} else {
				// noch n sekunden
				LobbyCountdown--;
			}
		};

		Starting = () -> {
			if (StartingCountdown == 0) {
				unSchedule(StartingID);
				GameID = schedule(Game, 0, GameID);
				StartingCountdown = FileManager.config.getInt("Countdown.Starting");
			} else {
				// noch n sekunden
				StartingCountdown--;
			}
		};

		Game = () -> {
			if (arena.players.size() == 0) {
				unSchedule(GameID);
				RestartingID = schedule(Restarting, 20, RestartingID);
			}
		};

		Restarting = () -> {
			if (RestartingCountdown == 0) {
				unSchedule(RestartingID);
				RestartingCountdown = FileManager.config.getInt("Countdown.Starting");
				arena.start();
			} else {
				// noch n sekunden
				RestartingCountdown--;
			}
		};
	}

	private String schedule(Runnable task, long update, String ID) {
		if (ID.equals("")) {
			return "" + scheduler.scheduleSyncRepeatingTask(MultiArenaMain.main, task, update, 0);
		}
		return ID;
	}

	private void unSchedule(String ID) {
		if (!ID.equals("")) {
			scheduler.cancelTask(Integer.parseInt(ID));
		}
	}
 }
