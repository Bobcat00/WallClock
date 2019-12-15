WallClock
Version 0.2.2c

Original Author: Adam Liszka
Revised by: Bobcat00

Change log:
0.2.2c - Changed schedule of async repeating task to sync repeating task to conform with Bukkit requirements.
         Added soft depend of Multiverse-Core to allow clocks to be placed in other worlds.

Copyright 2012 Adam Liszka
Copyright 2014 Bobcat00

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program.  If not, see <http://www.gnu.org/licenses/>.

+=======+
| Usage |
+=======+

All clocks must be placed exclusively on a wall. Stand alone signs are ignored
	as clocks.

=*                  *=
=* Basic Game Clock *=
=*                  *=

To create a simple clock showing world time, create a wall sign with the
	following on the first line:

+---------------+
|  [gameclock]  |
|               |
|               |
|               |
+---------------+

=*                     *=
=* Advanced Game Clock *=
=*                     *=

To create a more advanced clock, create a wall sign with the following:

+---------------+
|  [gameclock]  |
|   ClockLabel  |
|               |
|     Format    |
+---------------+

ClockLabel becomes "as is" line 2 on the clock. When no label is specified,
	it defaults to "Time" for Minecraft world clocks, and the short hand
	timezone for clocks showing time in real world.

The third line is unused and any text is ignored.

Format alters how the time is displayed. This text is passed to Java's
	SimpleDateFormat class, so see that doc for all the formats available.
	Clocks default to 12hr time, or as otherwise specified in the config file.

Note: All fields are optional and independent of each other


=*                      *=
=* The Real World Clock *=
=*                      *=

To create a clock that displays the real time, make a sign with the following
	on the first line:

+---------------+
|  [realclock]  |
|               |
|               |
|               |
+---------------+


=*                           *=
=* Advanced Real World Clock *=
=*                           *=

To further customize your clock, create a wall sign with the following:

+---------------+
|  [realclock]  |
|   ClockLabel  |
|   TimeZone    |
|     Format    |
+---------------+

ClockLabel becomes "as is" line 2 on the clock. When no label is specified, it
	defaults to "Time" for Minecraft world clocks, and the short hand timezone
	for clocks showing time in real world.

TimeZone affects the timezone of clocks displaying real time, otherwise it is
	ignored. By default, the system timezone is used, but it may not be correct
	depending on what OS the server is running on due to a bug in Java.

Format alters how the time is displayed. This text is passed to Java's
	SimpleDateFormat class, so see that doc for all the formats available.
	Clocks default to 12hr time, or as otherwise specified in the config file.

Note: All fields are optional and independent of each other

=* Example *=

To create a clock with the label "IRL Time", displaying the Real world time
	with a GMT offset of -2 hours, make a sign with the following:

+---------------+
|  [realclock]  |
|    IRL Time   |
|      Real     |
|     GMT-2     |
+---------------+



+==========+
| Commands |
+==========+

The idea driving the plugin is the ability to create a clock without leaving
	the 'world' of minecraft by opening up the chat window, and as such there
	are almost no commands. The commands presently provided are available to
	the console and OPs only for maintenance and other mischief tinkers want
	to get into.

/wallclock save

Manually save all the clocks in the world to disk ("plugins/WallClock/clocks.csv").

/wallclock load

Manually load the saved clocks from disk ("plugins/WallClock/clocks.csv"). This
	WILL render any new clocks created since the last save non functional.