package net.virushd.core.commands;

import net.virushd.core.main.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
				if (PlayerManager.isNormal(p)) {
					if (p.hasPermission("virushd.core.command.lobby") || p.hasPermission("*")) {

						// already in lobby
						if (PlayerManager.getPlayers().contains(p)) {
							switch (args.length) {
								case 0:

									// join the lobby again
									PlayerManager.join(p);
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

							// join
							PlayerManager.join(p);

							// remove from other minigames

							if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
								net.virushd.citybuild.main.PlayerManager.leave(p);
							}

							if (CoreMain.pluginAvailable("VirusHD-Creative")) {
								net.virushd.creative.main.PlayerManager.leave(p);
							}

							if (CoreMain.pluginAvailable("VirusHD-TTT")) {
								net.virushd.ttt.main.PlayerManager.leave(p);
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
