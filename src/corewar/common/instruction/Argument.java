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

import corewar.common.exceptions.IllegalScopeErrorException;

/**
 * Class represents an argument of a RedCodeInstruction.
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
public class Argument {
	/**
	 * The address-mode of an argument.
	 */
	private final Mode mode;
	/**
	 * The value of an argument.
	 */
	private final Value value;
	/**
	 * Shows if an Argument is actually used or unused/default (#0).
	 */
	private final boolean used;
	/**
	 * Private Constructor. Is always called from other Argument constructors to
	 * build objects.
	 * @param mode
	 *            Address mode of the argument.
	 * @param value
	 *            Value of the argument.
	 * @param used
	 *            Shows if an Argument is default or not.
	 */
	private Argument(final Mode mode, final Value value, final boolean used) {
		this.mode = mode;
		this.value = value;
		this.used = used;
		assert mode != null : "Mode has been initialized!!";
		assert value != null : "Value has been initalized!!";
	}
	/**
	 * Constructor.
	 * @param mode
	 *            Address mode of the argument.
	 * @param value
	 *            Value of the argument.
	 */
	public Argument(final Mode mode, final Value value) {
		this(mode, value, true);
	}

	/**
	 * Private default constructor. Sets mode immediate and value 0.
	 * @throws IllegalScopeErrorException
	 *             core size is wrong
	 */
	public Argument() throws IllegalScopeErrorException {
		this(Mode.immediate, new Value(0), false);
	}
	/**
	 * Generates the hasCode of an object.
	 * @return the hashCode.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (mode == null ? 0 : mode.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}
	/**
	 * Equals as known.
	 * @return true = Object equals obj / false = Object is different to obj.
	 * @param obj
	 *            Object to compare.
	 */
	public boolean equals(final Object obj) {
		boolean result = true;
		if (this != obj) {
			if (obj == null)
				result = false;
			if (getClass() != obj.getClass())
				result = false;
			final Argument other = (Argument) obj;
			if (mode != other.mode)
				result = false;
			if (!value.equals(other.value))
				result = false;
		}
		return result;
	}
	/**
	 * String description of an argument object.
	 * @return String description of the argument object.
	 */
	public String toString() {
		return getMode().getPrefix() + getValue().toString();
	}
	/**
	 * Getter for the mode.
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}
	/**
	 * Getter for the value.
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
	/**
	 * Getter for the field 'Used'.
	 * @return the isUsed
	 */
	public boolean isUsed() {
		return used;
	}
}
