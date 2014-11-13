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
package corewar.filter;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter - janek.schoenwetter@yahoo.com
 *
 */
public class NoBlankReader extends FilterReader {

	/**
	 * @param reader
	 */
	public NoBlankReader(final Reader reader) {
		super(reader);
	}

	/**
	 *
	 */
	public int read() throws IOException {
		int Withespace = 0;
		Withespace = super.read();
		while (Character.isWhitespace(Withespace))
			Withespace = super.read();
		return Withespace;
	}

	/**
	 *
	 */
	public int read(final char[] line) throws IOException {
		int character = -1;
		for (int i = 0; i < line.length; i++) {
			character = read();
			line[i] = (char) character;
		}
		return character;
	}
}
