package net.virushd.coins.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.coins.main.FileManager;
import net.virushd.core.main.PlaceHolder;

public class Coins implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			String NoPerm = PlaceHolder.withPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NoPerm"), p);
			String Usage = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Usage"), p);
			String PlayerNotFound = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.PlayerNotFound"), p);
			String CheckSelf = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CheckSelf"), p);
			String NotANumber = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.NotANumber"), p);

			if (cmd.getName().equalsIgnoreCase("coins")) {
				if (p.hasPermission("virushd.coins.extra") || p.hasPermission("*")) {

					// check argument length
					switch (args.length) {
						case 0:
							p.sendMessage(Usage);
							break;

						case 1: // 1 argument (check)

							if (args[0].equalsIgnoreCase("check")) {
								p.sendMessage(CheckSelf);
							} else {
								p.sendMessage(Usage);
							}
							break;

						case 2: // 2 arguments (check other)

							if (args[0].equalsIgnoreCase("check")) {

								// check player
								Player t1 = Bukkit.getPlayerExact(args[1]);
								if (t1 == null) {
									p.sendMessage(PlayerNotFound);
									break;
								}

								String CheckOthers = PlaceHolder.withPlayer(FileManager.messages.getString("Messages.CheckOthers"), t1);

								p.sendMessage(CheckOthers);
							} else {
								p.sendMessage(Usage);
							}
							break;

						case 3: // 3 arguments (set, remove, add)

							// check player
							Player t2 = Bukkit.getPlayerExact(args[1]);
							if (t2 == null) {
								p.sendMessage(PlayerNotFound);
							} else {
								try {

									// set amount
									int amount = Integer.parseInt(args[2]);

									// check argument (add, remove, set)
									if (args[0].equalsIgnoreCase("add")) {
										CoinsAPI.Coins.add(t2.getUniqueId(), amount);
										p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Add1"), t2).replace("{Amount}", "" + amount));
										t2.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Add2"), t2).replace("{Amount}", "" + amount));

									} else if (args[0].equalsIgnoreCase("remove")) {
										CoinsAPI.Coins.remove(t2.getUniqueId(), amount);
										p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Remove1"), t2).replace("{Amount}", "" + amount));
										t2.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Remove2"), t2).replace("{Amount}", "" + amount));

									} else if (args[0].equalsIgnoreCase("set")) {
										CoinsAPI.Coins.set(t2.getUniqueId(), amount);
										p.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Set1"), t2));
										t2.sendMessage(PlaceHolder.withPlayer(FileManager.messages.getString("Messages.Set2"), t2));
									}

								} catch (NumberFormatException e) {
									p.sendMessage(NotANumber);
								}
							}
							break;

						default:
							p.sendMessage(Usage);
							break;
					}
				} else if (p.hasPermission("virushd.coins.normal")) {
					p.sendMessage(CheckSelf);
				} else {
					p.sendMessage(NoPerm);
				}
			}
		}
		return false;
	}
}
