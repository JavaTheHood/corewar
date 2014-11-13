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
package corewar.common.constants;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public final class GlobalsMARS {

	/**
	 *
	 */
	public static final boolean SHOOTOUT = getProb("shootout", false);

	/**
	 *
	 */
	public static final int LENGTH_OF_DC_INST = 12;
	/**
	 *
	 */
	public static final int MAX_CYCLES = getProb("maxcycles",
			Constants.CORE_SIZE * 100); // Norm

	/**
	 *
	 */
	private GlobalsMARS() {
		assert false : "Utility class, without instances!";
	}

	/**
	 * Getter for a system property given Boolean value.
	 *
	 * @param probName
	 *            Name of the property.
	 * @param defValue
	 *            default value if system property isn't used.
	 * @return Boolean value, can be the default value or a system property.
	 */
	private static boolean getProb(final String probName, final boolean defValue) {
		final String name = System.getProperty(probName);
		boolean result = defValue;
		if (name != null)
			result = Boolean.parseBoolean(name);
		return result;
	}

	/**
	 * Getter for a system property given Integer value.
	 *
	 * @param probName
	 *            Name of the property.
	 * @param defValue
	 *            default value if system property isn't used.
	 * @return Integer value, can be the default value or a system property.
	 */
	private static int getProb(final String probName, final int defValue) {
		final String name = System.getProperty(probName);
		int result = defValue;
		if (name != null)
			result = Integer.parseInt(name);
		return result;
	}

}
