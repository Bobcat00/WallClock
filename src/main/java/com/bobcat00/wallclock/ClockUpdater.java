/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: A thread that updates all clocks in the
 * 		plugins's list of clocks.
 * Todo: Update clocks only when they need to be updated.
 * 		Probably means the removal of this class in favor
 * 		of a signal/observer based solution.
 */

package com.bobcat00.wallclock;

import com.bobcat00.wallclock.Clocks.Clock;


// This class handles updating the clock times once they are placed
// The list of clocks in the world is stored in the WallClockPlugin
// class.
public class ClockUpdater implements Runnable {
	private WallClockPlugin m_plugin;
	
	public ClockUpdater(WallClockPlugin plugin) {
		m_plugin = plugin;
	}
	
	public void run() {
		for (int i = 0; i < m_plugin.clocks.size(); i++) {
			Clock clock = m_plugin.clocks.get(i);
			if (clock.isLoaded()) {
				if (clock.isValid()) {
					clock.update();
				} else {
					m_plugin.log.info("Removing clock with label: '" + clock.getLabel() + "'");
					m_plugin.clocks.remove(i);
					i -= 1;
				}
			}
		}
	}
}