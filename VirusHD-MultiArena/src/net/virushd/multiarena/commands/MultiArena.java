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
//			String Usage = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Usage"), p);
//			String NotANumber = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.NotANumber"), p);
//			String WrongID = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.WrongID"), p);
//			String Arenas = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.Arenas"), p);
//			String ArenasFormat;
	
			if (cmd.getName().equalsIgnoreCase("multiarena")) {
				if (CoreMain.isNormal(p)){
					
					/*
					 * Normal
					 */
					
					if (!(p.hasPermission("virushd.multiarena.command.multiarena") || p.hasPermission("*"))) {
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
// 						switch (args.length) {
//						case 0:
//							p.sendMessage(Usage);
//							break;
//							
//						// arenas
//						case 1:
//							if (args[0].equalsIgnoreCase("arenas")) {
//							
//								// arenas
//								p.sendMessage(Arenas);
//								for (int i = 1; i <= 10; i++) { // das mit "i = 1" und "<=" so lassen VirusHD!
//									ArenasFormat = PlaceHolder.WithPlayer(FileManager.messages.getString("Messages.ArenasFormat"), p);
//									if (FileManager.arenas.contains("" + i)) {
//										ArenasFormat.replace("{ID}", "" + i);
//										ArenasFormat.replace("{Name}", FileManager.arenas.getString(i + ".Name"));
//										p.sendMessage(ArenasFormat);
//									}
//								}
//								break;
//							} else {
//								p.sendMessage(Usage);
//								break;
//							}
//							
//						// create, delete
//						case 2: 
//							if (args[0].equalsIgnoreCase("create")) {
//								ArenaManager.CreateArena(args[1]);
//								break;
//							} else if (args[0].equalsIgnoreCase("delete")) {
//								
//								// delete
//								try {
//									int id = Integer.parseInt(args[1]);
//									if (FileManager.arenas.contains("" + id)) {
//										ArenaManager.DeleteArena(id);
//										break;
//									} else {
//										p.sendMessage(WrongID);
//										break;
//									}
//								} catch (NumberFormatException ex) {
//									p.sendMessage(NotANumber);
//									break;
//								}
//							} else {
//								p.sendMessage(Usage);
//								break;
//							}
//						
//						// edit
//						case 3:
//							if (args[0].equalsIgnoreCase("edit")) {
//								
//								// edit
//								try {
//									int id = Integer.parseInt(args[1]);
//									if (args[2].equalsIgnoreCase("addspawn") || args[2].equalsIgnoreCase("deletespawns") || args[2].equalsIgnoreCase("setlobby")) {
//										if (FileManager.arenas.contains("" + id)) {
//											EditArena(id, args[2], p);
//											break;
//										} else {
//											p.sendMessage(WrongID);
//											break;
//										}
//									} else {
//										p.sendMessage(Usage);
//										break;
//									}
//								} catch (NumberFormatException ex) {
//									p.sendMessage(NotANumber);
//									break;
//								}
//							} else {
//								p.sendMessage(Usage);
//								break;
//							}
//						default:
//							p.sendMessage(Usage);
//							break;
//						}
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
	
/*	public static void EditArena(int id, String c, Player p) {
		switch (c) {
		case "addspawn":
			Location spawn = p.getLocation();
			int MaxPlayers = FileManager.config.getInt("MaxPlayers");
			for (int i = 1; i <= MaxPlayers; i++) { // das mit "i = 1" und "<=" so lassen VirusHD!
				if (FileManager.arenas.getString(id + ".Spawns." + i) == null) {
					SaveUtils.SaveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Spawns." + i, spawn);
					ArenaManager.LoadArena(id);
					break;
				}
			}
		case "deletespawns":
			FileManager.arenas.set(id + ".Spawns.", null);
			break;
		case "setlobby":
			Location lobby = p.getLocation();
			SaveUtils.SaveLocationToFile(FileManager.arenasF, FileManager.arenas, id + ".Lobby", lobby);
			ArenaManager.LoadArena(id);
			break;
			
		default:
			System.err.println("Fatal error in MultiArena command!");
			break;
		}
	}*/
}