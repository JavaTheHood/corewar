/* Roman Podolski - r.podolski@web.de, Janek Schoenwetter - janek.schoenwetter@yahoo.com
 * Praktikum Softwareentwicklung II, SS2011
 * Geotelematik und Navigation (GO1b), Hochschule München
 *   ____
 *  / ___|___  _ __ _____      ____ _ _ __
 * | |   / _ \| '__/ _ \ \ /\ / / _` | '__|
 * | |__| (_) | | |  __/\ V  V / (_| | |
 *  \____\___/|_|  \___| \_/\_/ \__,_|_|
 *
 * Sun Microsystems Inc. Java 1.6.0_24,
 * Windows 7 Enterprise, Windows 7 Starter
 * CANTIA-(Intel(R) Core(TM)2 Duo CPU 2.26GHz, 2267 MHz)
 * ASUS Eee PC (Intel(R) Atom(TM) CPU N550 @ 1,50 GHz)
 */
package corewar.filter.mars;

import java.io.IOException;
import java.io.Writer;

import corewar.common.constants.GlobalsMARS;
import corewar.common.core.Core;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;
import corewar.common.program.Program;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventParser;
import corewar.pipe.eventlog.EventType;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public final class Scheduler {

	/**
	 *
	 */
	private Scheduler() {
		assert false : "Utility class without instances!";
	}

	/**
	 *
	 * @param core
	 * @param print
	 * @param warriors
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */
	static void run(final Core core, final Writer print,
			final Program... warriors) throws SyntaxErrorException, IOException {
		if(GlobalsMARS.MAX_CYCLES == 0)
			assert false : "more than one repition!";
		int cycles = 0;
		// Schleife vereinfachen!
		while (theyCanFight(warriors) && cycles < GlobalsMARS.MAX_CYCLES) {
			for (final Program warrior : warriors){
				warrior.step(core, cycles, print);
				if (!warrior.isAlive()) {
					print.append(EventParser.printEvent(new Event(
							EventType.Kill, warrior.getIndex(), 0, 0,
							new Value(0), new Instruction())));
					if (!theyCanFight(warriors))
						print.append(EventParser.printEvent(new Event(
								EventType.Win, lastManStanding(warriors)
										.getIndex(), 0, 0, new Value(0),
								new Instruction())));
						break;
				}
			}
			cycles++;
		}
		if(cycles >= GlobalsMARS.MAX_CYCLES)
			print.append(EventParser.printEvent(new Event(EventType.Win,
					warriors.length, 0, 0, new Value(0), new Instruction())));
	}

	/**
	 *
	 * @param warriors
	 * @return
	 */
	private static Program lastManStanding(final Program[] warriors) {
		assert !theyCanFight(warriors) : "A winner exists!";
		Program lastManStanding = null;
		for (final Program program : warriors)
			if (program.isAlive())
				lastManStanding = program;
		return lastManStanding;
	}

	/**
	 *
	 * @param warriors
	 * @return
	 */
	private static boolean theyCanFight(final Program[] warriors) {
		boolean result = false;
		int stillStanding = 0;
		for (final Program warrior : warriors)
			if (warrior.isAlive())
				stillStanding++;
		if (stillStanding > 1)
			result = true;
		return result;
	}
}
