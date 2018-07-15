package net.virushd.troll.commands;

import net.virushd.core.main.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.api.PlaceHolder;
import net.virushd.troll.main.TrollMain;

public class Troll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			String NoPerm = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NoPerm"), p);

			if (cmd.getName().equalsIgnoreCase("troll")) {
				if (p.hasPermission("virushd.troll.command.troll") || p.hasPermission("*")) {
					if (PlayerManager.isNormal(p) || PlayerManager.isAdmin(p)) {

						// set to troll when the player isn't in troll mode yet
						PlayerManager.setTroll(p);
						// TODO Troll mode message

						// debug
						if (CoreMain.debug()) {
							TrollMain.main.getLogger().info("DEBUG: " + p.getName() + " entered troll mode.");
						}

					} else if (PlayerManager.isTroll(p)) {
						switch (args.length) {
							case 0:

								// set back to normal
								PlayerManager.setNormal(p);

								// debug
								if (CoreMain.debug()) {
									TrollMain.main.getLogger().info("DEBUG: " + p.getName() + " left troll mode.");
								}

								break;
							case 1: // TODO Troll-Menu einführen

								break;
							default: // TODO Usage

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
