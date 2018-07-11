package net.virushd.multiarena.commands;

import net.virushd.multiarena.inventories.Admin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.core.main.CoreMain;
import net.virushd.core.main.SaveUtils;
import net.virushd.core.main.PlaceHolder;
import net.virushd.multiarena.arena.ArenaManager;
import net.virushd.multiarena.main.FileManager;

public class MultiArena implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			
			Player p = (Player) sender;
				
			String NotInMode = PlaceHolder.WithPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NotInMode"), p);
			String NoPerm = PlaceHolder.WithPlayer(net.virushd.core.main.FileManager.messages.getString("Messages.NoPerm"), p);
	
			if (cmd.getName().equalsIgnoreCase("multiarena")) {
				if (CoreMain.isNormal(p)){
					
					/*
					 * Normal
					 */
					if ((p.hasPermission("virushd.multiarena.command.multiarena") || p.hasPermission("*"))) {
						// TODO Normal multiarena command
					} else {
						p.sendMessage(NoPerm);
					}
				} else if (CoreMain.isAdmin(p)) {
					
					/*
					 * Admin
					 */
					if (p.hasPermission("virushd.multiarena.command.multiarena") || p.hasPermission("*")) {
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