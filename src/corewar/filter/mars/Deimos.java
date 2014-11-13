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
package corewar.filter.mars;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import corewar.common.constants.Constants;
import corewar.common.constants.GlobalsMARS;
import corewar.common.core.Core;
import corewar.common.exceptions.IllegalScopeErrorException;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;
import corewar.common.program.Program;
import corewar.filter.NoBlankReader;
import corewar.pipe.decimalcode.Parser;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventParser;
import corewar.pipe.eventlog.EventType;

/**
 * A Memory Array Redcode Simulator (MARS). "Deimos" is the name of a moon of
 * the planet mars.
 *
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public class Deimos implements MarsCore {

	/*
	 * (non-Javadoc)
	 *
	 * @see corewar.filter.mars.MarsCore#runBattle(java.io.Writer,
	 * java.io.Reader[])
	 */
	@Override
	public void runBattle(final Writer protocolWriter,
			final Reader... redcodeReaders) throws IOException {
		final PrintWriter print = new PrintWriter(protocolWriter);
		try {
		final Core core = initCore(print, redcodeReaders);
		final char[] decimalcode = new char[GlobalsMARS.LENGTH_OF_DC_INST];
		final Program[] warriors = new Program[redcodeReaders.length];
		for (int program = 0; program < redcodeReaders.length; program++) {
			final NoBlankReader redcodeReader = new NoBlankReader(
					redcodeReaders[program]);
			final List<Instruction> instStorage = toList(decimalcode,
					redcodeReader);
			warriors[program] = load(core, redcodeReaders.length, instStorage,
					program, print);
		}
			Scheduler.run(core, print, warriors);
		} catch (final SyntaxErrorException e) {
			assert false : "Constructors have been testet and work correctly!";
		}
	}

	/**
	 *
	 * @param core
	 * @param programCount
	 * @param instStor
	 * @param program
	 * @param print
	 * @throws IOException
	 * @throws SyntaxErrorException
	 */
	private Program load(final Core core, final int programCount,
			final List<Instruction> instStor, final int program,
			final Writer print) throws IOException, SyntaxErrorException {
		final int shootout = generateShootout(programCount, instStor);
		int counter = 0;
		for (final Instruction instruction : instStor) {
				final Value address = new Value(Constants.CORE_SIZE
 / programCount
					* program + shootout + counter);
			core.load(address, instruction, print);
				// thread Index???
			final Event event = new Event(EventType.Load, program, 0,
						counter, address, instruction);
				print.append(EventParser.printEvent(event));
			counter++;
		}
		final Program warrior = new Program(new Value(Constants.CORE_SIZE
				/ programCount
 * program + shootout), program);
		return warrior;
	}
	/**
	 *
	 * @param programCount
	 * @param instStor
	 * @return
	 */
	private int generateShootout(final int programCount,
			final List<Instruction> instStor) {
		int shootout = 0;
		if (GlobalsMARS.SHOOTOUT) {
			final Random generator = new Random();
			shootout = generator.nextInt(Constants.CORE_SIZE / programCount
					- instStor.size());
		}
		return shootout;
	}

	/**
	 *
	 * @param decimalcode
	 * @param redcodeReader
	 * @return
	 * @throws IOException
	 */
	private List<Instruction> toList(final char[] decimalcode,
			final Reader redcodeReader) throws IOException {
		final List<Instruction> instStor = new ArrayList<Instruction>();
		// Schleife läuft über Instructionen
		while (redcodeReader.read(decimalcode) != -1)
			instStor.add(Parser.toInstruction(decimalcode));
		return instStor;
	}

	/**
	 *
	 * @param print
	 * @param redcodeReaders
	 * @return
	 * @throws IOException
	 * @throws SyntaxErrorException
	 * @throws IllegalScopeErrorException
	 */
	private Core initCore(final Writer print, final Reader... redcodeReaders)
			throws IOException, SyntaxErrorException {
		final Core core = new Core(Constants.CORE_SIZE);
			print.append(EventParser.printEvent(new Event(EventType.Init,
				redcodeReaders.length - 1, Constants.CORE_SIZE - 1,
					GlobalsMARS.MAX_CYCLES - 1, new Value(
							Constants.CORE_SIZE - 1), new Instruction())));
		return core;
	}
}
