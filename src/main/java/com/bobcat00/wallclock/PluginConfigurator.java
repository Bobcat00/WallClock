/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: Reads the configuration file for the plugin.
 */

package com.bobcat00.wallclock;

import org.bukkit.configuration.file.YamlConfiguration;

public class PluginConfigurator {
	public static void readConfig(String filename, WallClockPlugin plugin) {
		try {
			YamlConfiguration config = new YamlConfiguration();
			config.load(filename);
			WallClockPlugin.defaultTimeFormat = config.getString("defaultTimeFormat", WallClockPlugin.defaultTimeFormat);
			
		} catch (Exception e) {
			plugin.log.warning("Could not read config file!");
			plugin.log.warning(e.getMessage());
		}
	}
}
