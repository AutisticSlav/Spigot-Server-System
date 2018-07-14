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
import net.virushd.creative.main.CreativeMain;

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
					if (CoreMain.isNormal(p) || CoreMain.isTroll(p)) {

						// set admin
						CoreMain.setAdmin(p);
						p.sendMessage(AdminMode);

						// remove the players form other minigames
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
									CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
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

						// debug
						if (CoreMain.debug()) {
							CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " entered admin mode.");
						}

					} else if (CoreMain.isAdmin(p)) {
						switch (args.length) {
							case 0:

								// set back to normal
								CoreMain.setNormal(p);

								// debug
								if (CoreMain.debug()) {
									CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left admin mode.");
								}

								break;
							case 1:

								// open the admin menu
								if (args[0].equals("menu")) {
									if (CoreMain.isAdmin(p)) {
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
