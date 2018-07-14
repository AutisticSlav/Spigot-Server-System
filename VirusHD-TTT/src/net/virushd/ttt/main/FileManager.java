package net.virushd.ttt.main;

import java.io.File;
import java.util.Arrays;

import net.virushd.core.main.SaveUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	public static File messagesF;
	public static FileConfiguration messages;
	public static File configF;
	public static FileConfiguration config;
	public static File arenasF;
	public static FileConfiguration arenas;
	public static File sco_lobbyF;
	public static FileConfiguration sco_lobby;
	public static File sco_ingameF;
	public static FileConfiguration sco_ingame;

	@SuppressWarnings("static-access")
	public static void manager() {

		// messages
		messagesF = new File("plugins/VirusHD-TTT", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("TTTPrefix", "&0[&4TTT&0] &7");
		messages.addDefault("ChatFormat", "&0[&3Chat&0] &c{PlayerName}&0:&7 {Message}");
		messages.addDefault("Messages.TTT", "{TTTPrefix}Tippe &c/TTT&7.");
		messages.addDefault("Messages.NoJoinPerm", "{Prefix}&4Du hast keine Rechte &4TTT &7zu betreten.");
		messages.addDefault("Messages.Full", "{Prefix}&4TTT &7ist voll. Versuche es später noch einmal.");
		messages.addDefault("Messages.AlreadyStarted", "{TTTPrefix}Das Spiel hat leider schon begonnen :/");
		messages.addDefault("Countdown.Lobby.Message", "{TTTPrefix}Die Runde beginnt in &c{Time} &7Sekunden!");
		messages.addDefault("Countdown.Starting.Title", "{Time}");
		messages.addDefault("Countdown.Starting.Message", "{TTTPrefix}TTT beginnt in &c{Time} &7Sekunden!");
		messages.addDefault("Countdown.Starting.Final", "{TTTPrefix}TTT hat begonnen. Viel Glück!");
		messages.addDefault("Countdown.Restarting.Message", "{TTTPrefix}Das Spiel startet in &c{Time} &7Sekunden neu!");
		messages.addDefault("Join.Message", "{TTTPrefix}&c{PlayerName}&7 hat das Spiel betreten.");
		messages.addDefault("Join.Title", "&4Wilkommen");
		messages.addDefault("Join.SubTitle", "&c{PlayerName} &7in &4TTT");
		messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &4TTT\n&7-----------------------------------");
		messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		messages.addDefault("Quit.Message", "{TTTPrefix}&c{PlayerName}&7 hat das Spiel verlassen.");

		// das wurde noch nicht benuzt
		messages.addDefault("Messages.Usage", "&7»->--------------<-« &cUsage &7»->--------------<-«"
				+ "\n&c - /ttt hilfe &7Die Hilfe zum Spielmodus."
				+ "\n&c - /ttt commands &7Eine Liste der wichtigsten Commands."
				+ "\n&c - /ttt regeln &7Die Regeln.");
		messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
				+ "\n&c - &7Spiel Einfach!");
		messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
				+ "\n&c - /msg: &7Mit einem Spieler chatten."
				+ "\n&c - /coins: &7Der Coins command."
				+ "\n&c - /lobby: &7Kehre zur Lobby zurück.");
		messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
				+ "\n&c - &7Fair spielen."
				+ "\n&c - &7Spam ist untersagt."
				+ "\n&c - &7Nicht Hacken."
				+ "\n&c - &7Bei Fragen sich an den Support wenden."
				+ "\n&c - &7Bei Bugs einen Screen machen und ihn mit einer genauen Schilderung dem Admin schicken.");
		messages.options().copyDefaults(true);
		SaveUtils.saveFile(messagesF, messages);

		// config
		configF = new File("plugins/VirusHD-TTT", "config.yml");
		config = new YamlConfiguration().loadConfiguration(configF);
		config.addDefault("MaxPlayers", 10);
		config.addDefault("MinPlayers", 2);
		config.addDefault("Countdown.Lobby", 10);
		config.addDefault("Countdown.Starting", 10);
		config.addDefault("Countdown.Restarting", 5);
		config.addDefault("GameStates.Lobby", "&aLobby");
		config.addDefault("GameStates.LobbyFull", "&eLobby");
		config.addDefault("GameStates.Ingame", "&4Ingame");
		config.addDefault("Sign.Lines.0", "&7- &0[&4TTT&0] &7-");
		config.addDefault("Sign.Lines.1", "&c{Name}");
		config.addDefault("Sign.Lines.2", "&0[&r{GameState}&0]");
		config.addDefault("Sign.Lines.3", "&c{Players} &7/ &c{MaxPlayers}");
		config.options().copyDefaults(true);
		SaveUtils.saveFile(configF, config);

		// arenas
		arenasF = new File("plugins/VirusHD-TTT", "arenas.yml");
		arenas = new YamlConfiguration().loadConfiguration(arenasF);

		// TODO Scoreboard Lobby
		// sco_lobby
		sco_lobbyF = new File("plugins/VirusHD-TTT/scoreboards", "lobby.yml");
		sco_lobby = new YamlConfiguration().loadConfiguration(sco_lobbyF);
		SaveUtils.defaultScoreboardToFile(sco_lobby, "Lobby", "&4VirusHD.net &7- &4TTT", Arrays.asList("{Space}", "&cRang:", "&7{Rank}", "{Space}", "Lobby"));
		sco_lobby.options().copyDefaults(true);
		SaveUtils.saveFile(sco_lobbyF, sco_lobby);

		// TODO Scoreboard Ingame
		// sco_ingame
		sco_ingameF = new File("plugins/VirusHD-TTT/scoreboards", "ingame.yml");
		sco_ingame = new YamlConfiguration().loadConfiguration(sco_ingameF);
		SaveUtils.defaultScoreboardToFile(sco_ingame, "Ingame", "&4VirusHD.net &7- &4TTT", Arrays.asList("{Space}", "&cRang:", "&7{Rank}", "{Space}", "Ingame"));
		sco_ingame.options().copyDefaults(true);
		SaveUtils.saveFile(sco_ingameF, sco_ingame);
	}
}
