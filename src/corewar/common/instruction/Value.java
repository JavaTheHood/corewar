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

import corewar.common.constants.Constants;
import corewar.common.exceptions.IllegalScopeErrorException;
/**
 * Class symbolizes the value of an RedCode instruction argument.
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
public class Value {
	/**
	 * Usable value.
	 */
	private final int argumentValue;

	/**
	 * Constructor.
	 * @param value
	 *            Value of an argument.
	 * @throws IllegalScopeErrorException
	 *             the core size is too big or lower 0
	 */
	public Value(final int value) throws IllegalScopeErrorException {
		if (Constants.CORE_SIZE > Constants.MAX_SCOPE
				|| Constants.CORE_SIZE <= 0)
			throw new IllegalScopeErrorException(
					Integer.toString(Constants.CORE_SIZE));
		if (value % Constants.CORE_SIZE < 0)
			argumentValue = Constants.CORE_SIZE + value % Constants.CORE_SIZE;
		else
			argumentValue = value % Constants.CORE_SIZE;

		assert 0 <= argumentValue && argumentValue < Constants.CORE_SIZE : "argumentValue is in a allowed scope!";
	}
	/**
	 * Getter for the value.
	 * @return the value
	 */
	public int getArgumentValue() {
		return argumentValue;
	}
	/**
	 * String description of an Value object.
	 * @return String description of the Value object.
	 */
	public String toString() {
		return Integer.toString(getArgumentValue());
	}
	/**
	 * Generates the hasCode of an object.
	 * @return the hashCode.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + argumentValue;
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
		if (this == obj)
			return true;
		if (obj == null)
			result = false;
		if (getClass() != obj.getClass())
			result = false;
		final Value other = (Value) obj;
		if (argumentValue != other.argumentValue)
			result = false;
		return result;
	}
}
