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
package corewar.pipe.eventlog;

import corewar.pipe.decimalcode.Decimalcode;

/**
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter - janek.schoenwetter@yahoo.com
 *
 */
public final class EventParser {

	/**
	 * Blocked CTor.
	 */
	private EventParser() {
		assert false : "Utility class without Instances!";
	}

	/**
	 *
	 * @return
	 */
	public static String printEvent(final Event event) {
		final String eventLog = event.getEventType().getPrefix()
				+ String.format("%01d%04d%06d%04d",
 event.getProgrammIndex(),
						event.getThreadIndex(), event.getCycle(), event
								.getAddress().getArgumentValue())
				+ Decimalcode.encode(event.getInstruction())
				+ String.format("%n");
		return eventLog;
	}
}
