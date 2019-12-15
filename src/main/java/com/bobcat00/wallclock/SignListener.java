/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: Listens for clocks that have "[gameclock]" or
 * 		"[realclock]" on the first line of the sign. There is no
 * 		listener for block destroyed events because clocks check
 * 		if they are still valid in the ClockUpdater loop and remove
 * 		themselves if they are no longer attached to a sign. It may
 * 		be the case that the clock is improperly read from file or
 * 		otherwise directed to a non sign block. The sign formats
 * 		are as follows:
 * 
 * 		[gameclock]
 * 		ClockLabel
 * 		-unused at this time-
 * 		ClockFormat
 * 
 * 		[realclock]
 * 		ClockLabel
 * 		TimeZone
 * 		ClockFormat
 * 
 *		ClockLabel becomes "as is" line 2 on the clock
 *  	TimeZone affects the timezone of SystemClock clocks
 *  	ClockFormat changes the format of the clock (i.e. "hh:mm")
 *  
 *   	Lines 1-3 are entirely optional.
 *   
 * Todo: Permissions...
 */

package com.bobcat00.wallclock;

import com.bobcat00.wallclock.Clocks.GameClock;
import com.bobcat00.wallclock.Clocks.SystemClock;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {
	protected WallClockPlugin plugin;
	
	public SignListener(WallClockPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void blockEvent(SignChangeEvent event) {
		String[] lines = event.getLines();
		if (lines[0].equals("[gameclock]")) {
			GameClock clock = new GameClock(event.getBlock());
			clock.setLabel(lines[1]);
			
			clock.setFormat(lines[3]);
			
			plugin.addClock(clock);
			
		} else if (lines[0].equals("[realclock]")) {
			SystemClock clock = new SystemClock(event.getBlock());
			clock.setLabel(lines[1]);
			clock.setTimeZone(lines[2]);
			clock.setFormat(lines[3]);

			plugin.addClock(clock);
		}
	}
}
