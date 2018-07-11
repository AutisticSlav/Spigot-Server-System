package net.virushd.citybuild.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.virushd.core.main.SaveUtils;
import net.virushd.inventory.main.InventoryAPI;

public class FileManager {

	public static File messagesF;
	public static FileConfiguration messages;
	public static File configF;
	public static FileConfiguration config;
	public static File inv_plotF;
	public static FileConfiguration inv_plot;
	public static File sco_citybuildF;
	public static FileConfiguration sco_citybuild;

	@SuppressWarnings("static-access")
	public static void Manager() {

		// messages
		messagesF = new File("plugins/VirusHD-CityBuild", "messages.yml");
		messages = new YamlConfiguration().loadConfiguration(messagesF);
		messages.addDefault("CityBuildPrefix", "&0[&6CityBuild&0] &7");
		messages.addDefault("ChatFormat", "&0[&3Chat&0] &c{PlayerName}&0:&7 {Message}");
		messages.addDefault("Messages.Usage","&7»->--------------<-« &cUsage &7»->--------------<-«"
											+ "\n&c - /citybuild hilfe &7Die Hilfe zum Spielmodus."
											+ "\n&c - /citybuild commands &7Eine Liste der wichtigsten Commands."
											+ "\n&c - /citybuild regeln &7Die Regeln."
											+ "\n&c - /citybuild tp <Weltname> &7Teleportiert dich in eine andere Welt."
											+ "\n&c - /citybuild plot &7Hiermit kanns du einen Plot erstellen.");
		messages.addDefault("Messages.WrongWorld", "{CityBuildPrefix}Bitte gebe als Welt &ccitybuild&7, &cfarmwelt &7oder &cnether &7ein.");
		messages.addDefault("Messages.CityBuild", "{CityBuildPrefix}Tippe &c/citybuild&7.");
		messages.addDefault("Messages.NoJoinPerm", "{Prefix}&4Du hast keine Rechte &6CityBuild &7zu betreten.");
		messages.addDefault("Messages.Full", "{Prefix}&6CityBuild &7ist voll. Versuche es später noch einmal.");
		messages.addDefault("Messages.Pos1", "{CityBuildPrefix}Position 1 gesetzt!");
		messages.addDefault("Messages.Pos2", "{CityBuildPrefix}Position 2 gesetzt!");
		messages.addDefault("Messages.Delete", "{CityBuildPrefix}Dein Plot wurde gelöscht.");
		messages.addDefault("Messages.Create", "{CityBuildPrefix}Du hast erfolgreich deinen Plot erstellt.");
		messages.addDefault("Messages.AlreadyAPlot", "{CityBuildPrefix}Du hast bereits einen Plot.");
		messages.addDefault("Messages.NoPlot", "{CityBuildPrefix}Du besitzt keinen Plot.");
		messages.addDefault("Messages.OnlyInCityBuildWorld", "{CityBuildPrefix}&7Bitte benutze diesen Befehl nur in der CityBuild-Welt.");
		messages.addDefault("Messages.PlotCollides", "{CityBuildPrefix}&cFehler: &4Der Plot Kollidiert mit einem anderen Plot.");
		messages.addDefault("Messages.PlotTooBig", "{CityBuildPrefix}&cFehler: &4Der Plot ist zu gross.");
		messages.addDefault("Quit.Message", "{CityBuildPrefix}&c{PlayerName}&7 hat &6CityBuild &7verlassen.");
		messages.addDefault("Join.Message", "{CityBuildPrefix}&c{PlayerName}&7 hat &6CityBuild &7betreten.");
		messages.addDefault("Join.Title", "&4Wilkommen");
		messages.addDefault("Join.SubTitle", "&c{PlayerName} &7in &6CityBuild");
		messages.addDefault("TabTitle.Header", "&4VirusHD.net &7- &6CityBuild\n&7-----------------------------------");
		messages.addDefault("TabTitle.Footer", "&7-----------------------------------\n&cViel Spass!");
		messages.addDefault("Messages.Help", "&7»->--------------<-« &cDie Hilfe &7»->--------------<-«"
											+ "\n&c - &7Zuerst brauchst du ein Grundstück. Laufe zuerst ein bisschen weg vom Spawn und tippe dann &c/citybuild plot&7."
											+ "\n&c - &7Setze mit &c/sethome <name> &7ein Zuhause bei deinem Plot. (Man kann mehrere Homes mit unterschiedlichen Namen setzen.)"
											+ "\n&c - &7Du kannst dich jetzt immer mit &c/home <name> &7dort hin teleportieren."
											+ "\n&c - &7Um zu farmen gehe in die Farmwelt."
											+ "\n&c - &7Nachdem du gefarmt hast, kannst du dir ein Haus Bauen und halt einfach normal Minecraft spielen."
											+ "\n&c - &7Viel spass in CityBuild. (Bald gibts einen Shop.)");
		messages.addDefault("Messages.Commands", "&7»->--------------<-« &cDie Commands &7»->--------------<-«"
											+ "\n&c - /citybuild &7Hauptcommand des Spielmodus."
											+ "\n&c - /msg &7Mit einem Spieler chatten."
											+ "\n&c - /home &7Zu Homes teleportieren."
											+ "\n&c - /sethome &7Homes erstellen."
											+ "\n&c - /delhome &7Homes löschen."
											+ "\n&c - /coins &7Der Coins command."
											+ "\n&c - /lobby &7Kehre zur Lobby zurück."
											+ "\n&c Nur für &4V&9I&aP &c/ &fYou&4Tuber&c:"
											+ "\n&c - /skull &7Gibt dir einen kopf von dir."
											+ "\n&c - /feed &7Deine Hungerbalken werden aufgefüllt.");
		messages.addDefault("Messages.Rules", "&7»->--------------<-« &cDie Regeln &7»->--------------<-«"
											+ "\n&c - &7Fair spielen."
											+ "\n&c - &7Spam ist untersagt."
											+ "\n&c - &7Nicht Hacken."
											+ "\n&c - &7Bei Fragen sich an den Support wenden."
											+ "\n&c - &7Bei Bugs einen Screen machen und ihn mit einer genauen Schilderung dem Admin schicken.");
		messages.options().copyDefaults(true);
		SaveUtils.SaveFile(messagesF, messages);

		// config
		configF = new File("plugins/VirusHD-CityBuild", "config.yml");
		config = new YamlConfiguration().loadConfiguration(configF);
		SaveUtils.DefaultLocationToFile(config, "Spawns.CityBuild", Bukkit.getWorld("CityBuild").getSpawnLocation());
		SaveUtils.DefaultLocationToFile(config, "Spawns.Farmworld", Bukkit.getWorld("Farmwelt").getSpawnLocation());
		SaveUtils.DefaultLocationToFile(config, "Spawns.Nether", Bukkit.getWorld("world_nether").getSpawnLocation());
		config.addDefault("GameStates.Lobby", "&aLobby");
		config.addDefault("GameStates.LobbyFull", "&eLobby");
		config.addDefault("MaxPlayers", 10);
		config.addDefault("Sign.Lines.1", "&7- &0[&6CityBuild&0] &7-");
		config.addDefault("Sign.Lines.2", "&c{Name}");
		config.addDefault("Sign.Lines.3", "&0[&r{GameState}&0]");
		config.addDefault("Sign.Lines.4", "&c{Players} &7/ &c{MaxPlayers}");
		config.addDefault("MaxPlotSize", 250);
		config.options().copyDefaults(true);
		SaveUtils.SaveFile(configF, config);

		// inv_plot
		inv_plotF = new File("plugins/VirusHD-CityBuild/inventories", "plot.yml");
		inv_plot = new YamlConfiguration().loadConfiguration(inv_plotF);
		inv_plot.addDefault("Inventory.DisplayName", "&cPlot-Menu");
		SaveUtils.DefaultItemToFile(inv_plot, "Items.Pos1", InventoryAPI.createItem("&cPosition 1", Arrays.asList("&7Setze die erste position des Plots."), Material.REDSTONE_TORCH_ON, null, 1));
		SaveUtils.DefaultItemToFile(inv_plot, "Items.Pos2", InventoryAPI.createItem("&cPosition 2", Arrays.asList("&7Setze die zweite position des Plots."), Material.REDSTONE_TORCH_ON, null, 1));
		SaveUtils.DefaultItemToFile(inv_plot, "Items.Delete", InventoryAPI.createItem("&cLöschen", Arrays.asList("&7Lösche den Plot."), Material.TNT, null, 1));
		SaveUtils.DefaultItemToFile(inv_plot, "Items.Create", InventoryAPI.createItem("&cErstellen", Arrays.asList("&7Erstelle den Plot."), Material.WORKBENCH, null, 1));
		inv_plot.options().copyDefaults(true);
		SaveUtils.SaveFile(inv_plotF, inv_plot);

		// sco_citybuild
		sco_citybuildF = new File("plugins/VirusHD-CityBuild/scoreboards", "citybuild.yml");
		sco_citybuild = new YamlConfiguration().loadConfiguration(sco_citybuildF);
		SaveUtils.DefaultScoreboardToFile(sco_citybuild, "CityBuild", "&4VirusHD.net &7- &6CityBuild", Arrays.asList("{Space}", "&cRang:", "&7{Rank}", "{Space}", "&cCoins:", "&7{Coins}", "{Space}", "&cWelt:", "&7{World}"));
		sco_citybuild.options().copyDefaults(true);
		SaveUtils.SaveFile(sco_citybuildF, sco_citybuild);
	}
}
