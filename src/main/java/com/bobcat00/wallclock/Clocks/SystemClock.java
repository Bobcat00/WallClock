/*
 * Author: Adam Liszka
 * License: See LICENSE.txt
 * Description: An implementation of a clock representing real world time.
 * 				The sign text format (i.e. "[realclock]" stuff) is defined
 * 				in the SignListener class where it belongs. This clock
 * 				takes time zones into consideration, and setFormat(String)
 * 				was overrode to represent this.
 * 
 */

package com.bobcat00.wallclock.Clocks;

import java.util.Date;
import java.util.TimeZone;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SystemClock extends Clock {

	protected TimeZone m_timezone = TimeZone.getDefault();
	
	public SystemClock(Block signBlock) {
		super(signBlock);
	}
	
	public void update() {
		if (!isValid()) return;
		
		Sign s = sign();
		
		s.setLine(0, "");
		if (!m_label.isEmpty()) {
			s.setLine(1, m_label);
		} else {
			s.setLine(1, m_formatter.getTimeZone().getID());
		}
		s.setLine(2, m_formatter.format(new Date()));
		s.setLine(3, "");
		
		s.update();
	}
	
	@Override
	public void setFormat(String format) {
		super.setFormat(format);
		m_formatter.setTimeZone(m_timezone);
		//m_format = m_formatter.toPattern();
	}
	
	// TimeZone methods
	public void setTimeZone(String timezone) {
		m_timezone = TimeZone.getTimeZone(timezone);
		m_formatter.setTimeZone(m_timezone);
	}
	
	public TimeZone getTimezone() {
		return m_timezone;
	}
	
}
