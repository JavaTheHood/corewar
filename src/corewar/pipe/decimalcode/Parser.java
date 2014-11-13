package corewar.pipe.decimalcode;

import corewar.common.constants.GlobalsMARS;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Argument;
import corewar.common.instruction.Command;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Mode;
import corewar.common.instruction.Value;

/**
 *
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public final class Parser {

	/**
	 *
	 */
	private Parser() {
		assert false : "Utility class without instances!";
	}

	/**
	 *
	 * @param decimalcode
	 * @return
	 */
	public static Instruction toInstruction(final char[] decimalcode) {
		assert decimalcode.length == GlobalsMARS.LENGTH_OF_DC_INST : "Decimalcode Instruction length suits norm";
		final String toParse = String.valueOf(decimalcode);
		Instruction instruction = null;
		Argument direction = null;
		Argument start = null;
		int counter = 2;
		final Command commmand = Command.values()[Integer.parseInt(toParse
				.substring(0, counter))];
		try {
			start = new Argument(Mode.values()[Integer.parseInt(toParse
					.substring(counter++, counter))], new Value(
Integer.parseInt(toParse.substring(counter++,
							counter + 2 + 1))));
			direction = new Argument(Mode.values()[Integer.parseInt(toParse
					.substring(counter + 2 + 1, GlobalsMARS.LENGTH_OF_DC_INST
							- counter))],
					new Value(Integer.parseInt(toParse.substring(
							GlobalsMARS.LENGTH_OF_DC_INST - counter,
							GlobalsMARS.LENGTH_OF_DC_INST))));
			instruction = new Instruction(commmand, start, direction);
		} catch (final SyntaxErrorException e) {
			try {
				instruction = new Instruction(commmand, direction);
			} catch (final SyntaxErrorException e1) {
				assert false : "Unreachable code, Decimalcode is correct!!";
			}
		}
		return instruction;
	}
}
