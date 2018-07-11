package net.virushd.core.commands;

import net.virushd.ttt.arena.Arena;
import net.virushd.ttt.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.core.main.SetLobby;
import net.virushd.creative.main.CreativeMain;

public class Lobby implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			
			Player p = (Player) sender;
				
			String NotInMode = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NotInMode"), p);
			String NoPerm = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoPerm"), p);
			String Usage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.LobbyUsage"), p);
			String Help = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Help"), p);
			String Commands = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Commands"), p);
			String Rules = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Rules"), p);
	
			if (cmd.getName().equalsIgnoreCase("lobby")) {
				if (CoreMain.isNormal(p)) {
					if (p.hasPermission("virushd.core.command.lobby") || p.hasPermission("*")) {
						if (CoreMain.players.contains(p)) {
							switch (args.length) {
							case 0:
								SetLobby.setLobby(p);
								break;
							case 1:
								if (args[0].equalsIgnoreCase("hilfe") || args[0].equalsIgnoreCase("help")) {
									
									// help
									p.sendMessage(Help);
									break;
								} else if (args[0].equalsIgnoreCase("commands")) {
									
									// commands
									p.sendMessage(Commands);
									break;
								} else if (args[0].equalsIgnoreCase("regeln") || args[0].equalsIgnoreCase("rules")) {
									
									// rules
									p.sendMessage(Rules);
									break;
								} else {
									p.sendMessage(Usage);
								}
								break;
							default:
								p.sendMessage(Usage);
								break;
							}
						} else {
							SetLobby.setLobby(p);
							if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
								if (CityBuildMain.players.contains(p)) {
									CityBuildMain.players.remove(p);

									String QuitMessage = PlaceHolder.WithPlayer(net.virushd.citybuild.main.FileManager.messages.getString("Quit.Message"), p);

									for (Player players : CityBuildMain.players) {
										players.sendMessage(QuitMessage);
									}

									// debug
									if (CoreMain.debug()) {
										CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
									}
								}
							}

							if (CoreMain.pluginAvailable("VirusHD-Creative")) {
								if (CreativeMain.players.contains(p)) {
									CreativeMain.players.remove(p);

									String QuitMessage = PlaceHolder.WithPlayer(net.virushd.creative.main.FileManager.messages.getString("Quit.Message"), p);

									for (Player players : CreativeMain.players) {
										players.sendMessage(QuitMessage);
									}

									// debug
									if (CoreMain.debug()) {
										CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left Creative.");
									}
								}
							}

							if (CoreMain.pluginAvailable("VirusHD-TTT")) {
								for (Arena a : ArenaManager.getArenas()) {
									if (a.getPlayers().contains(p)) {
										a.getPlayers().remove(p);

										String QuitMessage = PlaceHolder.WithPlayer(net.virushd.ttt.main.FileManager.messages.getString("Quit.Message"), p);

										for (Player players : a.getPlayers()) {
											players.sendMessage(QuitMessage);
										}

										// debug
										if (CoreMain.debug()) {
											CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left TTT.");
										}
									}
								}
							}
						}
					} else {
						p.sendMessage(NoPerm);
					}
				} else {
					p.sendMessage(NotInMode);
				}
			}
		}
		return false;
	}
}
