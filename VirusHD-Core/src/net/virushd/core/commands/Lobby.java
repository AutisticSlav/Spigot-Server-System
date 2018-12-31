package net.virushd.core.commands;

import net.virushd.core.api.Minigame;
import net.virushd.core.api.PlaceHolder;
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

			String NotInMode = PlaceHolder.withPlayer(FileManager.getMessage("Messages.NotInMode"), p);
			String NoPerm = PlaceHolder.withPlayer(FileManager.getMessage("Messages.NoPerm"), p);
			String Usage = PlaceHolder.withPlayer(FileManager.getMessage("Messages.LobbyUsage"), p);
			String Help = PlaceHolder.withPlayer(FileManager.getMessage("Messages.Help"), p);
			String Commands = PlaceHolder.withPlayer(FileManager.getMessage("Messages.Commands"), p);
			String Rules = PlaceHolder.withPlayer(FileManager.getMessage("Messages.Rules"), p);

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

							// remove the players form other minigames
							for (Minigame minigame : CoreMain.getMinigames()) {
								minigame.leave(p);
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
