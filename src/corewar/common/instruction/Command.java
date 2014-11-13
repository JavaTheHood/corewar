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
 * Enum class for RedcodeCommands.
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
public enum Command {
	/**
	 * Constant value a. Not executable.
	 */
	DAT,
	/**
	 * Copy a to b.
	 */
	MOV,
	/**
	 * Add a to b.
	 */
	ADD,
	/**
	 * Jump to a.
	 */
	JMP,
	/**
	 * Jump to a, if b = 0.
	 */
	JMZ,
	/**
	 * Lower b about 1; jump to a, if then b = 0.
	 */
	DJZ,
	/**
	 * skip the next instruction, if a != b.
	 */
	CMP,
	/**
	 * Execute the program at the next instruction and at a.
	 */
	SPL;
	/**
	 * Private method tests, if a Instruction has two Arguments.
	 *
	 * @return true = has two arguments / false = has only one argument.
	 */
	public boolean hasTwoArguments() {
		return !(equals(DAT) || equals(JMP) || equals(SPL));
	}
}
