/* Roman Podolski - r.podolski@web.de
 * Janek Schoenwetter - janek.schoenwetter@yahoo.com
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
package corewar.pipe.sourcecode;

import corewar.common.constants.Constants;
import corewar.common.exceptions.IllegalNumberOfArgumentsErrorException;
import corewar.common.exceptions.IllegalScopeErrorException;
import corewar.common.exceptions.InvalidCommandErrorExcepion;
import corewar.common.exceptions.NumberFormatErrorExcepion;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Argument;
import corewar.common.instruction.Command;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Mode;
import corewar.common.instruction.Value;
/**
 * Generates an Instruction Object. Class defines methods to parse Redcode
 * source code into Instruction objects. The Redcode command is splitted into an
 * Instruction Object with its right Command and Arguments.
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 * @version 1.0
 */
public final class InstructionParser {
	/**
	 * Private Default constructor. Has been blocked.
	 */
	private InstructionParser() {
		assert false : "Utility class without instances";
	}
	/**
	 * Parses a String to a Instructions object.
	 * Checks the correct syntax of the Redcode command. The syntax should be
	 * right, if not an Exception will be thrown.
	 * @return An Instruction object.
	 * @param line
	 *            A RedCode command as String.
	 * @throws SyntaxErrorException
	 *             Illegal Instruction, wrong syntax.
	 */
	public static Instruction parseInstruction(final String line)
			throws SyntaxErrorException {
		final String toParse = stripComment(line);
		if (toParse.isEmpty())
			return null;
		return buildInstruction(splitTokens(toParse));
	}
	/**
	 * Strips the commend of a Redcode instruction.
	 * @param line
	 *            String description of a RedCodeInstruction.
	 * @return String description of a RedCodeInstruction, without comment and
	 *         useless trailing spaces.
	 */
	private static String stripComment(final String line) {
		String result;
		if (line.contains(";"))
			result = line.substring(0, line.indexOf(';')).trim();
		else
			result = line.trim();
		return result;
	}
	/**
	 * Build an Array of Strings. Saves the parts the String description of a
	 * RedCodeInstruction in an array. Cuts the whitespace.
	 * @param line
	 *            String description of a RedCodeInstruction.
	 * @return String array with the parts of a RedCodeInstruction.
	 */
	private static String[] splitTokens(final String line) {

		return line.split("\\s+");
	}
	/**
	 * Build the Instruction Object. The Array will be checked for its right
	 * number of Arguments if there is not the right number, an Exception will
	 * be thrown. The Method uses other Methods to generate the right
	 * instruction.
	 *
	 * @param token
	 *            Array of Strings with parts of a RedCodeInstruction.
	 * @return A new Instruction object.
	 * @throws SyntaxErrorException
	 *             Illegal Command, wrong syntax
	 */
	private static Instruction buildInstruction(final String[] token)
			throws SyntaxErrorException {
		Instruction instruction = null;
		if (token.length == Constants.MIN_ARGUMENT)
			instruction = new Instruction(parseCommand(token[0]),
					parseArgument(token[1]));
		else if (token.length == Constants.MAX_ARGUMENT)
			instruction = new Instruction(parseCommand(token[0]),
					parseArgument(token[1]), parseArgument(token[2]));
		else
			throw new IllegalNumberOfArgumentsErrorException(token);
		assert instruction != null : "intruction has been initialized!";
		return instruction;
	}
	/**
	 * Parses a String to a Command object. The Method allocates the right
	 * Command to the instruction. Throws an InvalidCommandErrorException if
	 * there's no (right) command
	 * @param toParse
	 *            String contains a Command.
	 * @return Command object from String.
	 * @throws InvalidCommandErrorExcepion
	 *             String can not be parsed to a command.
	 */
	private static Command parseCommand(final String toParse)
			throws InvalidCommandErrorExcepion {
		Command rcCommand = null;
		for (final Command command : Command.values())
			if (toParse.equalsIgnoreCase(command.toString()))
				rcCommand = command;
		if (rcCommand == null)
			throw new InvalidCommandErrorExcepion(toParse);
		return rcCommand;
	}

	/**
	 * Parses a String to an Argument object. The Method allocates the right
	 * Argument to the instruction.
	 *
	 * @param line
	 *            String to parse.
	 * @return new Argument object from string.
	 * @throws NumberFormatErrorExcepion
	 *             String can not be parsed to an Argument.
	 * @throws IllegalScopeErrorException
	 *             core size is wrong
	 */
	private static Argument parseArgument(final String line)
			throws NumberFormatErrorExcepion, IllegalScopeErrorException {

		assert !line.isEmpty() : "token exists!";

		Argument argument = null;
		try {
			for (final Mode mode : Mode.values())
				if (line.startsWith(mode.getPrefix())
						&& !mode.equals(Mode.direct))
					argument = new Argument(mode, new Value(
							Integer.parseInt(line.substring(1)
									.replace('+', ' ').trim())));
			if (Character.isDigit(line.charAt(0)) || line.charAt(0) == '-'
					|| line.charAt(0) == '+')
				argument = new Argument(Mode.direct, new Value(
						Integer.parseInt(line.replace('+', ' ').trim())));
		} catch (final NumberFormatException e) {
			throw new NumberFormatErrorExcepion(e.getMessage().toString());
		}
		if (argument == null)
			throw new NumberFormatErrorExcepion(line);
		return argument;
	}

}
