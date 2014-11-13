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

import corewar.common.exceptions.IllegalAddressModeErrorException;
import corewar.common.exceptions.IllegalNumberOfArgumentsErrorException;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.useful.Useful;
/**
 * A RedCodeInstruction object.
 *
 * @author Roman Podolski, Janek Schoenwetter
 * @version 1.0
 */
public class Instruction {
	/**
	 * Object field for a RedCode command.
	 */
	private final Command command;
	/**
	 * Object field for a start argument.
	 */
	private final Argument start;
	/**
	 * Object field for a direction argument.
	 */
	private final Argument direction;
	/**
	 * Constructor for an Instruction object of the form: command, argument,
	 * argument.
	 *
	 * @param command
	 *            Command of the instruction.
	 * @param start
	 *            Start argument of the instruction
	 * @param direction
	 *            Direction argument of the instruction.
	 * @throws SyntaxErrorException
	 *             will be thrown if the number of arguments doesn't match the
	 *             expected norm.
	 */
	public Instruction(final Command command, final Argument start,
			final Argument direction) throws SyntaxErrorException {
		this.command = command;
		this.start = start;
		this.direction = direction;
		if (command.hasTwoArguments() != start.isUsed())
			throw new IllegalNumberOfArgumentsErrorException(
					command.toString(), start.toString(), direction.toString());
		if (!hasCorrectAdressMode(command, start, direction))
			throw new IllegalAddressModeErrorException(command.toString(),
					start.toString(), direction.toString());
		assert command != null : "command has been initialized!";
		assert start != null : "start has been initialized!";
		assert direction != null : "direction has been initialized!";
	}
	/**
	 * Constructor for an Instruction object of the form: command, argument.
	 * Only instructions with the commands DAT, JMP, SPL can have only one
	 * argument.
	 *
	 * @param command
	 *            A RedcodeCommand.
	 * @param argument
	 *            A RedcodeArgument.
	 * @throws SyntaxErrorException
	 *             will be thrown if the number of arguments doesn't match the
	 *             expected norm.
	 */
	public Instruction(final Command command, final Argument argument)
			throws SyntaxErrorException {
		this(command, new Argument(), argument);
		if (command.hasTwoArguments())
			throw new IllegalNumberOfArgumentsErrorException(
					command.toString(), direction.toString());
	}

	/**
	 * Default constructor. Initializes a DAT #0 #0 Instruction object.
	 * @throws SyntaxErrorException
	 *             will be thrown if the number of arguments doesn't match the
	 *             expected norm.
	 */
	public Instruction() throws SyntaxErrorException {
		this(Command.DAT, new Argument());
	}
	/**
	 * Getter for the RedCode command.
	 * @return the command
	 */
	public Command getCommand() {
		return command;
	}
	/**
	 * Getter for the start argument.
	 * @return the start
	 */
	public Argument getStart() {
		return start;
	}
	/**
	 * Getter for the direction argument.
	 * @return the direction
	 */
	public Argument getDirection() {
		return direction;
	}
	/**
	 * String description of an Instruction object.
	 * @return String description of the Instruction object.
	 */
	public String toString() {
		String toString = "";
		if (!getCommand().hasTwoArguments())
			toString = Useful.buildComment(getCommand().toString(),
					getDirection()
					.toString());
		toString = Useful.buildComment(getCommand().toString(), getStart()
					.toString(), getDirection().toString());
		return toString;
	}
	/**
	 * Generates the hashCode of an object.
	 * @return the hashCode.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (command == null ? 0 : command.hashCode());
		result = prime * result
				+ (direction == null ? 0 : direction.hashCode());
		result = prime * result + (start == null ? 0 : start.hashCode());
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
		final Instruction other = (Instruction) obj;
		if (command != other.command)
			result = false;
		if (!direction.equals(other.direction))
			result = false;
		if (!start.equals(other.start))
			result = false;
		return result;
	}
	/**
	 * private static method, tests if a redcode command matches the adress
	 * modes of two red code arguments.
	 * @param directionArgument
	 *            the direction Argument of the RedCode Instruction
	 * @param startArgument
	 *            the start Argument of the RedCodeInstruction
	 * @param com
	 *            the command of the RedCodeInstruction
	 * @return true = address mode is correct/ false = address mode is
	 *         incorrect/invalid
	 */
	private static boolean hasCorrectAdressMode(final Command com,
			final Argument startArgument, final Argument directionArgument) {
		return testStartAdressMode(com, startArgument)
				&& testDirectionAdressMode(com, directionArgument);
	}
	/**
	 * Private static method, tests if a redcode command matches a redcode start
	 * argument.
	 * @param rcCom
	 *            redcode command to test.
	 * @param startArgument
	 *            redcode start argument to test.
	 * @return true = address mode is correct/ false = address mode is
	 *         incorrect/invalid
	 */
	private static boolean testStartAdressMode(final Command rcCom,
			final Argument startArgument) {
		boolean result = true;
		if (rcCom.equals(Command.JMZ) || rcCom.equals(Command.DJZ)
				&& startArgument.getMode().equals(Mode.immediate))
					result = false;
		return result;
	}
	/**
	 * Private static method, tests if a redcode command matches a redcode
	 * direction argument.
	 * @param rcCom
	 *            redcode command to test.
	 * @param directionArgument
	 *            redcode direction argument to test.
	 * @return true = address mode is correct/ false = address mode is
	 *         incorrect/invalid
	 */
	private static boolean testDirectionAdressMode(final Command rcCom,
			final Argument directionArgument) {
		boolean result = false;
		switch (directionArgument.getMode()) {
		case immediate:
			final Command[] commandArray = new Command[]{Command.DAT, Command.JMZ, Command.CMP};
			for (final Command com : commandArray)
				if (com.equals(rcCom))
					result = true;
			break;
		case direct:
		case indirect:
			for (final Command com : Command.values())
				if (com.equals(rcCom) && !com.equals(Command.DAT))
					result = true;
			break;
		default:
			break;
		}
		return result;
	}
}
