package net.virushd.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.virushd.citybuild.main.CityBuildMain;
import net.virushd.core.main.CoreMain;
import net.virushd.core.main.FileManager;
import net.virushd.core.main.PlaceHolder;
import net.virushd.creative.main.CreativeMain;

public class Admin implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			
			Player p = (Player) sender;
				
			String Usage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AdminUsage"), p);
			String AdminMode = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.AdminMode"), p);
			String NoPerm = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NoPerm"), p);
			
			if (cmd.getName().equals("admin")) {
				if (p.hasPermission("virushd.core.command.admin") || p.hasPermission("*")) {
					if (CoreMain.isNormal(p) || CoreMain.isTroll(p)) {
						CoreMain.setAdmin(p);
						p.sendMessage(AdminMode);
						
						if (Bukkit.getServer().getPluginManager().getPlugin("VirusHD-CityBuild") != null) {
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
						
						if (Bukkit.getServer().getPluginManager().getPlugin("VirusHD-Creative") != null) {
							if (CreativeMain.players.contains(p)) {
								CreativeMain.players.remove(p);
								
								String QuitMessage = PlaceHolder.WithPlayer(net.virushd.creative.main.FileManager.messages.getString("Quit.Message"), p);
								
								for (Player players : CreativeMain.players) {
									players.sendMessage(QuitMessage);
								}
								
								// debug
								if (CoreMain.debug()) {
									CreativeMain.main.getLogger().info("DEBUG: " + p.getName() + " left CityBuild.");
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
							CoreMain.setNormal(p);
							
							// debug
							if (CoreMain.debug()) {
								CoreMain.main.getLogger().info("DEBUG: " + p.getName() + " left admin mode.");
							}
							
							break;
						case 1:
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
		} return false;
	}
}
