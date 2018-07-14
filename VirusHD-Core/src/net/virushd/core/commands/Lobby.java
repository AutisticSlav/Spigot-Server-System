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

			String NotInMode = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NotInMode"), p);
			String NoPerm = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NoPerm"), p);
			String Usage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.LobbyUsage"), p);
			String Help = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Help"), p);
			String Commands = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Commands"), p);
			String Rules = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Rules"), p);

			if (cmd.getName().equalsIgnoreCase("lobby")) {
				if (CoreMain.isNormal(p)) {
					if (p.hasPermission("virushd.core.command.lobby") || p.hasPermission("*")) {

						// already in lobby
						if (CoreMain.getPlayers().contains(p)) {
							switch (args.length) {
								case 0:

									// set to lobby again
									SetLobby.setLobby(p);
									break;
								case 1:

									// help commands and rules
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

							// set to lobby
							SetLobby.setLobby(p);

							// remove from other minigames
							if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
								if (CityBuildMain.getPlayers().contains(p)) {
									CityBuildMain.getPlayers().remove(p);

									String QuitMessage = PlaceHolder.withPlayer(net.virushd.citybuild.main.FileManager.messages.getString("Quit.Message"), p);

									for (Player players : CityBuildMain.getPlayers()) {
										players.sendMessage(QuitMessage);
									}

									// debug
									if (CoreMain.debug()) {
										CityBuildMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
									}
								}
							}

							if (CoreMain.pluginAvailable("VirusHD-Creative")) {
								if (CreativeMain.getPlayers().contains(p)) {
									CreativeMain.getPlayers().remove(p);

									String QuitMessage = PlaceHolder.withPlayer(net.virushd.creative.main.FileManager.messages.getString("Quit.Message"), p);

									for (Player players : CreativeMain.getPlayers()) {
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

										String QuitMessage = PlaceHolder.withPlayer(net.virushd.ttt.main.FileManager.messages.getString("Quit.Message"), p);

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
