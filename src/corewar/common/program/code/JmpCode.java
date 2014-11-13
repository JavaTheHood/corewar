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
public class JmpCode implements CoreCode {

	@Override
	public void execute(final Core core, final Program program,
			final int cycles, final Instruction instruction, final Writer print)
			throws IOException, SyntaxErrorException {
		print.append(EventParser.printEvent(new Event(EventType.Execute,
				program.getIndex(), 0, cycles, program.getAddress(),
				instruction)));
		moveProgram(getDirectionAddress(instruction, core), program);
	}

	/**
	 *
	 * @param directionAddress
	 */
	private void moveProgram(final Value directionAddress, final Program program) {
		program.setAddress(directionAddress.getArgumentValue());
	}

	/**
	 *
	 * @param instruction
	 * @param core
	 * @return
	 */
	private Value getDirectionAddress(final Instruction instruction,
			final Core core) {
		Value value = null;
		if (instruction.getDirection().getMode() == Mode.direct)
			value = instruction.getDirection().getValue();
		else if (instruction.getDirection().getMode() == Mode.indirect)
			value = core.getInstrucion(instruction.getDirection().getValue())
					.getDirection().getValue();
		else
			assert false : "Instruction has been tested!";
		return value;
	}

}
