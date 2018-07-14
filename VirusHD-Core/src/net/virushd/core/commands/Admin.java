package net.virushd.core.commands;

import net.virushd.core.main.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class Admin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			String Usage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.AdminUsage"), p);
			String AdminMode = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.AdminMode"), p);
			String NoPerm = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NoPerm"), p);

			if (cmd.getName().equals("admin")) {
				if (p.hasPermission("virushd.core.command.admin") || p.hasPermission("*")) {

					// switch admin mode
					if (PlayerManager.isNormal(p) || PlayerManager.isTroll(p)) {

						// set admin
						PlayerManager.setAdmin(p);
						p.sendMessage(AdminMode);

						// remove the players form other minigames

						if (CoreMain.pluginAvailable("VirusHD-CityBuild")) {
							net.virushd.citybuild.main.PlayerManager.leave(p);
						}

						if (CoreMain.pluginAvailable("VirusHD-Creative")) {
							net.virushd.creative.main.PlayerManager.leave(p);
						}

						if (CoreMain.pluginAvailable("VirusHD-TTT")) {
							net.virushd.ttt.main.PlayerManager.leave(p);
						}

						// debug
						if (CoreMain.debug()) {
							CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " entered admin mode.");
						}

					} else if (PlayerManager.isAdmin(p)) {
						switch (args.length) {
							case 0:

								// set back to normal
								PlayerManager.setNormal(p);

								// debug
								if (CoreMain.debug()) {
									CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left admin mode.");
								}

								break;
							case 1:

								// open the admin menu
								if (args[0].equals("menu")) {
									if (PlayerManager.isAdmin(p)) {
										net.virushd.core.inventories.Admin.open(p);
									} else {
										p.sendMessage(Usage);
									}
								} else {
									p.sendMessage(Usage);
								}
								break;
							default:
								p.sendMessage(Usage);
								break;
						}
					}
				} else {
					p.sendMessage(NoPerm);
				}
			}
		}
		return false;
	}
}
