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
package corewar.common.instruction;
/**
 * Enum class for RedCode address modes.
 * @author Roman Podolski, Janek Schoenwetter.
 * @version 1.0
 */
public enum Mode {
	/**
	 * Value itself.
	 */
	immediate("#"),
	/**
	 * Value at address n.
	 */
	direct(""),
	/**
	 * Value at address, that is at address n.
	 */
	indirect("@");
	/**
	 * Getter for the prefix of an address mode.
	 */
	private final String prefix;
	/**
	 * Constructor.
	 * @param prefix
	 *            the prefix of the address mode - allowed are "#", "" and "@".
	 */
	private Mode(final String prefix) {
		this.prefix = prefix;
		assert "@".equals(prefix) || "#".equals(prefix) || prefix.isEmpty() : "prefix ist korrekt definiert";
	}
	/**
	 * Method returns a String description of a mode object.
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
}
