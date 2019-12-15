/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: The Clock base class. Provides time format, labeling, and
 * 				block attachment functionality, as well as functionality to
 * 				check if the sign is still valid. Override the update() method
 * 				to update the time on the face of the clock. The sign text
 * 				format (i.e. "[gameclock]" stuff) is defined in the SignListener
 * 				class where it belongs.
 * 
 */

package com.bobcat00.wallclock.Clocks;

import java.text.SimpleDateFormat;

import com.bobcat00.wallclock.WallClockPlugin;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public abstract class Clock {
	protected Block m_signBlock = null;
	protected String m_format = WallClockPlugin.defaultTimeFormat;
	protected SimpleDateFormat m_formatter = new SimpleDateFormat(m_format);
	protected String m_label = "Time";
	
	public Clock(Block signBlock) {
		m_signBlock = signBlock;
	}
	
	public Block getBlock() {
		return m_signBlock;
	}
	
	// Updates the text of the sign.
	public abstract void update();
	
	// Format methods
	public void setFormat(String format) {
		if (!format.isEmpty()) {
			m_formatter = new SimpleDateFormat(format);
			m_format = m_formatter.toPattern();
		}
	}
	
	public String getFormat() {
		return m_format;
	}
	
	public SimpleDateFormat formatter() {
		return m_formatter;
	}
	
	// Label methods
	public void setLabel(String label) {
		if (!label.isEmpty()) {
			m_label = label;
		}
	}
	
	public String getLabel() {
		return m_label;
	}
	
	// Check if this clock is still associated with a sign
	// CAVEAT! Block.getType return Material.AIR if the chunk is not loaded!
	public boolean isValid() {
		return m_signBlock.getType() == Material.WALL_SIGN;
	}
	
	public boolean isLoaded() {
		return m_signBlock.getChunk().isLoaded();
	}
	
	// Convenience method
	public Sign sign() {
		return (Sign)m_signBlock.getState();
	}
	
}