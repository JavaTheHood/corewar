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
package corewar.common.program.code;

import java.io.IOException;
import java.io.Writer;

import corewar.common.core.Core;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Argument;
import corewar.common.instruction.Command;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Mode;
import corewar.common.instruction.Value;
import corewar.common.program.Program;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventParser;
import corewar.pipe.eventlog.EventType;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public class MovCode implements CoreCode {

	@Override
	public void execute(final Core core, final Program program,
			final int cycles, final Instruction instruction, final Writer print)
			throws IOException, SyntaxErrorException {
		print.append(EventParser.printEvent(new Event(EventType.Execute,
				program.getIndex(), 0, cycles, program.getAddress(),
				instruction)));
		movInstruction(
					getInstructionToMov(instruction.getStart(), core, program
							.getAddress().getArgumentValue()), core, program
							.getAddress().getArgumentValue(),
				instruction.getDirection(), print);
	}

	/**
	 *
	 * @param instruction
	 * @param core
	 * @param programAdress
	 * @return
	 * @throws SyntaxErrorException
	 */
	private Instruction getInstructionToMov(final Argument start,
			final Core core, final int programAddress)
			throws SyntaxErrorException {
		final Mode mode = start.getMode();
		Instruction toMov = core.getInstrucion(new Value(programAddress
				+ start.getValue().getArgumentValue()));
			if (mode == Mode.immediate)
				toMov = new Instruction(Command.DAT, new Argument(
						Mode.immediate, start.getValue()));
			else if (mode == Mode.indirect)
			toMov = core.getInstrucion(new Value(programAddress
					+ start.getValue().getArgumentValue()
					+ toMov.getDirection().getValue().getArgumentValue()));
		return toMov;
	}

	/**
	 *
	 * @param instruction
	 * @param core
	 * @param programAdress
	 * @param argument
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */
	private void movInstruction(final Instruction instruction, final Core core,
			final int programAddress, final Argument direction,
			final Writer print) throws SyntaxErrorException, IOException {
		final Mode mode = direction.getMode();
		Value directValue;
		directValue = new Value(programAddress
				+ direction.getValue().getArgumentValue());
		if (mode == Mode.indirect)
			core.set(new Value(programAddress
					+ core.getInstrucion(directValue).getDirection().getValue()
							.getArgumentValue()), instruction, print);
		else if (mode == Mode.direct)
			core.set(directValue, instruction, print);
		else
			assert false : "Unreachable code!!";
	}
}
