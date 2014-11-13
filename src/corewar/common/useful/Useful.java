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
package corewar.common.useful;
/**
 * Static Class with useful methods. The class defines useful methods for every
 * situation.
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public final class Useful {
	/**
	 * Private Default constructor -blocked.
	 */
	private Useful() {
		assert false : "Utility class without instances!";
	}
	/**
	 * Build a String. The method uses a StringBuffer to create a String with
	 * whitespace.
	 * @param toComment
	 *            an Array of Strings.
	 * @return A String. (trimmed)
	 */
	public static String buildComment(final String... toComment) {
		final StringBuffer comment = new StringBuffer();
		for (final String element : toComment) {
			comment.append(element);
			comment.append(' ');
		}
		return comment.toString().trim();
	}

}
