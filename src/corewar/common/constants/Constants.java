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
 * Class initializes constants as system properties or by default values.
 * @author Roman Podolski, Janek Schoenwetter.
 * @version 1.0
 */
public final class Constants {
	/**
	 * Constant value for the core size, Integer-value.
	 */
	public static final int CORE_SIZE = getProb("coresize", 4000);
	/**
	 * Constant for the minimal number of arguments in a RedCode instruction.
	 */
	public static final int MIN_ARGUMENT = 2;
	/**
	 * Constant for the maximal number of arguments in a RedCode instruction.
	 */
	public static final int MAX_ARGUMENT = 3;
	/**
	 * Constant for the maximal (possible) core size.
	 */
	public static final int MAX_SCOPE = 10000;

	/**
	 * Constructor. Initializations blocked.
	 */
	private Constants() {
		assert false : "Utility class without instances";
	}
	/**
	 * Getter for a system property given Integer value.
	 * @param probName
	 *            Name of the property.
	 * @param defValue
	 *            default value if system property isn't used.
	 * @return Integer value, can be the default value or a system property.
	 */
	private static int getProb(final String probName, final int defValue){
		final String name = System.getProperty(probName);
		int result = defValue;
		if (name != null)
			result = Integer.parseInt(name);
		return result;
	}
}
