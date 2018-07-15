package net.virushd.ttt.commands;

import net.virushd.core.main.PlayerManager;
import net.virushd.ttt.inventories.Admin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.core.api.PlaceHolder;

public class TTT implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			String NotInMode = PlaceHolder.withPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NotInMode"), p);
			String NoPerm = PlaceHolder.withPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NoPerm"), p);

			if (cmd.getName().equalsIgnoreCase("ttt")) {
				if (PlayerManager.isNormal(p)) {

					/*
					 * Normal
					 */

					if ((p.hasPermission("virushd.ttt.command.ttt") || p.hasPermission("*"))) {
						// TODO Normal ttt command
					} else {
						p.sendMessage(NoPerm);
					}
				} else if (PlayerManager.isAdmin(p)) {

					/*
					 * Admin
					 */

					if (p.hasPermission("virushd.ttt.command.ttt") || p.hasPermission("*")) {
						Admin.open(p);
					} else {
						p.sendMessage(NoPerm);
					}
				} else {

					/*
					 * Troll
					 */

					p.sendMessage(NotInMode);
				}
			}
		}
		return false;
	}
}