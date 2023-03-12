/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: Handles all reading and writing of clock lists
 * 		to the disk. Presently uses a .csv format to store data
 * 		though that may be subject to change depending on future
 * 		needs of the plugin. NOTE: all comma's in the label are
 * 		replaced with spaces because I don't feel like dealing
 * 		with it at this point in time. 
 * 
 * Todo: Non-lazy csv reading and writing, or implement a better
 * 		storage format.
 */

package com.bobcat00.wallclock;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.bobcat00.wallclock.Clocks.Clock;
import com.bobcat00.wallclock.Clocks.GameClock;
import com.bobcat00.wallclock.Clocks.SystemClock;

import org.bukkit.Server;
import org.bukkit.block.Block;

// This class takes clocks and saves it to disk, as well
// as read clocks from the disk.
public class ClockSaver {
	
	// Write out a .csv file containing all clocks in the world, or rather an
    // almost proper csv. The clock label is shoved on the end and special
    // csv formatting is ignored because it complicates the code and is (at
    // this point) unnecessary.
	public static void write(String filename, ArrayList<Clock> clocks) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filename));
            for (Clock clock : clocks) {
            	Block block = clock.getBlock();
            	
            	if (clock instanceof SystemClock) {
            		out.print("SYSTEM" + ",");
            	} else if (clock instanceof GameClock) {
            		out.print("GAME" + ",");
            	}
            	
                out.write(block.getWorld().getName() + ",");
                out.write(block.getX() + ",");
                out.write(block.getY() + ",");
                out.write(block.getZ() + ",");
                
                if (clock instanceof SystemClock) {
            		out.write(((SystemClock)clock).getTimezone().getID() + ",");
            	} else if (clock instanceof GameClock) {
            		out.print(",");
            	}
                
                out.write(clock.getFormat() + ",");
                out.write(clock.getLabel().replaceAll(",", " ") + "\n");
            }
            out.flush();
            out.close();
		} catch (Exception e) {
			//log.info("[" + getDescription().getName() + "] Cannot save wall clocks!");
			e.printStackTrace();
		}
	}
	
	// We need a Server object because we derive the block location
    // based on coordinates that server provides
	public static ArrayList<Clock> read(String filename, Server server) {
		ArrayList<Clock> clocks = new ArrayList<Clock>();
		
		try {
			Scanner in = new Scanner(new FileReader(filename));
            while (in.hasNextLine()) {
            	Clock clock;
                String[] line = in.nextLine().split(",");
                
                int x = Integer.parseInt(line[2]);
                int y = Integer.parseInt(line[3]);
                int z = Integer.parseInt(line[4]);
                Block b = server.getWorld(line[1]).getBlockAt(x, y, z);

                if (line[0].equals("SYSTEM")) {
                	SystemClock sysclock = new SystemClock(b);
                	sysclock.setTimeZone(line[5]);
                	clock = sysclock;
                } else if (line[0].equals("GAME")) {
                	clock = new GameClock(b);
                } else {
                	continue;
                }
                
                clock.setFormat(line[6]);
                clock.setLabel(line[7]);

                clocks.add(clock);
            }
		} catch (Exception e) {
			//log.info("[" + getDescription().getName() + "] Cannot load wall clocks!");
			e.printStackTrace();
		}
		
		return clocks;
	}
	
}