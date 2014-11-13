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
package corewar.pipe.decimalcode;

import corewar.common.instruction.Instruction;

/**
 * Class generates decimal code.
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
public final class Decimalcode {
	/**
	 * Constructor. Initializations blocked.
	 */
	private Decimalcode() {
		assert false : "Utilityclass without instances";
	}

	/**
	 * Method encrypts RedCodeInstruction to decimal code.
	 * @param toEncode
	 *            an instruction object.
	 * @return a decimal code description of the RedCodeInstruction as a String.
	 */
	public static String encode(final Instruction toEncode) {

		final String result = String.format("%02d", toEncode.getCommand()
				.ordinal())
				+ toEncode.getStart().getMode().ordinal()
				+ String.format("%04d", toEncode.getStart().getValue()
						.getArgumentValue())
				+ toEncode.getDirection().getMode().ordinal()
				+ String.format("%04d", toEncode.getDirection().getValue()
						.getArgumentValue()) + '\n';
		return result;
	}

}
