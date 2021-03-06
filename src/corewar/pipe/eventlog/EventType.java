/* Roman Podolski - r.podolski@web.de, Janek Schoenwetter - janek.schoenwetter@yahoo.com
 * Praktikum Softwareentwicklung II, SS2011
 * Geotelematik und Navigation (GO1b), Hochschule M�nchen
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

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public enum EventType {

	/**
	 *
	 */
	Init('I'),
	/**
	 *
	 */
	Load('L'),
	/**
	 *
	 */
	Execute('X'),
	/**
	 *
	 */
	SetMem('M'),
	/**
	 *
	 */
	Kill('K'),
	/**
	 *
	 */
	Win('W');

	/**
	 *
	 */
	private final char prefix;

	/**
	 *
	 * @param prefix
	 */
	EventType(final char prefix) {
		this.prefix = prefix;
	}

	/**
	 * @return the prefix
	 */
	char getPrefix() {
		return prefix;
	}

}
