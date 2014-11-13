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
package corewar.common.exceptions;
import corewar.common.useful.Useful;
/**
 * Exception class for general syntactic errors in a RedCodeIntruction.
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SyntaxErrorException extends Exception {
	/**
	 * Variable for a description of the error.
	 */
	private final String error;
	/**
	 * Constructor.
	 * @param strings
	 *            String description of the code fragment, what caused the
	 *            exception.
	 * @param string
	 *            general description of the error.
	 */
	SyntaxErrorException(final String string, final String... strings) {
		super();
		error = string + Useful.buildComment(strings);
	}
	/**
	 * Getter for the description of the error.
	 * @return the error-description
	 */
	public String getError() {
		return error;
	}
}
