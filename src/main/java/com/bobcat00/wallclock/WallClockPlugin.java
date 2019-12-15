/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: The main plugin class. Holds the list of clocks
 * 		that are in the world, various plugin settings, and
 * 		sets up the ClockUpdater to run every second.
 */

package com.bobcat00.wallclock;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.bobcat00.wallclock.Clocks.Clock;

import org.bukkit.plugin.java.JavaPlugin;

public class WallClockPlugin extends JavaPlugin {
	public Logger log = Logger.getLogger("Minecraft");
	public ArrayList<Clock> clocks = new ArrayList<Clock>(); // A list of all placed clocks
	public String saveFile = "plugins/WallClock/clocks.csv";
	public ClockCommandExecutor cmdExecutor = new ClockCommandExecutor(this);
	public static String defaultTimeFormat = "HH:mm";

	public void onEnable() {
		PluginConfigurator.readConfig("plugins/WallClock/config.yml", this);
		
		//////
		// Set up the plugin directory if needed
		// TODO: Can this be cleaner?
		File save = new File(saveFile);
		
		if (!save.exists()) {
			if (!save.getParentFile().exists()) {
				if (save.getParentFile().mkdirs()) {
					log.info("[" + getDescription().getName() + "] Created settings folder");
				} else {
					log.info("[" + getDescription().getName() + "] Cannot create settings folder!");
				}
			}
		} else {
			clocks = ClockSaver.read(saveFile, getServer());
		}
		
		getCommand("wallclock").setExecutor(cmdExecutor);
		
		//////
		// Attach the sign listener
		SignListener listener = new SignListener(this);
		getServer().getPluginManager().registerEvents(listener, this);

		// 0 is the initial delay and 20 is one second (0/20 and 20/20 for time in seconds)
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new ClockUpdater(this), 0, 20);
	}

	public void onDisable() {
		// save ALL the clocks!
		ClockSaver.write(saveFile, clocks);
	}

	public void addClock(Clock clock) {
		clocks.add(clock);
	}
}
