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
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Mode;
import corewar.common.instruction.Value;
import corewar.common.program.Program;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventParser;
import corewar.pipe.eventlog.EventType;


/**
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter - janek.schoenwetter@yahoo.com
 *
 */
public class AddCode implements CoreCode {

	@Override
	public void execute(final Core core, final Program program,
			final int cycles, final Instruction instruction, final Writer print)
			throws IOException, SyntaxErrorException {
		print.append(EventParser.printEvent(new Event(EventType.Execute,
				program.getIndex(), 0, cycles, program.getAddress(),
				instruction)));
			// Wert der Addiert werden soll
		final Value toAdd = getValueToAdd(core, instruction.getStart(), program
				.getAddress().getArgumentValue());
		// Addier den Wert
			addValue(toAdd, instruction.getDirection(), program.getAddress()
				.getArgumentValue(), core, print);
	}

	/**
	 *
	 * @param toAdd
	 * @param direction
	 * @param addressOfInstruction
	 * @param core
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */
	private void addValue(final Value toAdd, final Argument direction,
			final int addressOfInstruction, final Core core, final Writer print)
			throws SyntaxErrorException, IOException {
		final Mode mode = direction.getMode();
		if (mode == Mode.indirect)
			core.update(
					new Value(addressOfInstruction
							+ core.getInstrucion(direction.getValue())
									.getDirection().getValue()
									.getArgumentValue()), toAdd, print);
		else if (mode == Mode.direct)
			core.update(new Value(addressOfInstruction
					+ direction.getValue().getArgumentValue()), toAdd, print);
		else
			assert false : "Unreachable Code";
	}

	/**
	 *
	 * @param core
	 * @param start
	 * @param addressOfInstruction
	 * @return
	 * @throws SyntaxErrorException
	 */
	// Testen!!
	private Value getValueToAdd(final Core core, final Argument start, final int addressOfInstruction) throws SyntaxErrorException {
		final Instruction direct = core.getInstrucion(new Value(addressOfInstruction + start.getValue().getArgumentValue()));
		Value toAdd = direct.getDirection().getValue();
		final Mode mode = start.getMode();
		if(mode == Mode.immediate)
			toAdd = start.getValue();
		else if(mode == Mode.indirect)
			toAdd = core.getInstrucion(new Value(addressOfInstruction + toAdd.getArgumentValue())).getDirection().getValue();
		return toAdd;
	}
}
