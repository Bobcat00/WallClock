/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: Handles commands sent by users and the console.
 * 		Presently, the only commands implemented are `save' and
 * 		`load' as backups for admins. The commands are intentionally
 * 		otherwise invisible to the normal user.
 * 
 * Todo: Permissions...
 */

package com.bobcat00.wallclock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClockCommandExecutor implements CommandExecutor {
	private WallClockPlugin m_plugin;
	 
	public ClockCommandExecutor(WallClockPlugin plugin) {
		m_plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player)sender).isOp()) {
			return true; // I don't really want to alert normal users to these commands
		}
		
		if (args.length != 1) {
			return false;
		}
		
		if (args[0].equalsIgnoreCase("save")) {
			ClockSaver.write(m_plugin.saveFile, m_plugin.clocks);
			sender.sendMessage("Saved All Clocks");
			return true;

		} else if (args[0].equalsIgnoreCase("load")) {
			m_plugin.clocks = ClockSaver.read(m_plugin.saveFile, m_plugin.getServer());
			sender.sendMessage("Loaded Saved Clocks");
			return true;
		}
		
		return false;
	}
}
