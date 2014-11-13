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
package corewar.common.core;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Argument;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventParser;
import corewar.pipe.eventlog.EventType;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public class Core {

	/**
	 *
	 */
	private final Instruction[] coreArray;

	/**
	 * @throws SyntaxErrorException
	 *
	 */
	public Core(final int coresize) throws SyntaxErrorException {
		coreArray = new Instruction[coresize];
		Arrays.fill(coreArray, new Instruction());
	}

	/**
	 * @return the coreArray
	 */
	public Instruction getInstrucion(final Value address) {
		return getCoreArray()[address.getArgumentValue()];
	}

	/**
	 * Only MOV
	 *
	 * @param address
	 * @param intruction
	 * @throws IOException
	 */
	public void set(final Value address, final Instruction instruction,
			final Writer print) throws IOException {
		load(address, instruction, print);
		print.append(EventParser.printEvent(new Event(EventType.SetMem, 0, 0,
				0, address, instruction)));
	}

	/**
	 * @return the coreArray
	 */
	private Instruction[] getCoreArray() {
		return coreArray;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Core [coreArray=" + Arrays.toString(coreArray) + "]";
	}

	/**
	 * Only ADD & DJZ
	 *
	 * @param address
	 * @param inc
	 * @throws IOException
	 */
	public void update(final Value address, final Value inc, final Writer print)
			throws IOException {
		Instruction instruction = null;
		try {
			instruction = new Instruction(
				getCoreArray()[address.getArgumentValue()].getCommand(),
				getCoreArray()[address.getArgumentValue()].getStart(),
				new Argument(getCoreArray()[address.getArgumentValue()]
						.getDirection().getMode(), new Value(
						getCoreArray()[address.getArgumentValue()]
								.getDirection().getValue().getArgumentValue()
									+ inc.getArgumentValue())));
		} catch (final SyntaxErrorException e) {
			assert false : "Scope has been testet already and is Correct!!";
		}
		set(address, instruction, print);
	}

	/**
	 *
	 * @param address
	 * @param instruction
	 * @param print
	 */
	public void load(final Value address, final Instruction instruction,
			final Writer print) {
		assert instruction != null : "Instruction exsitst!";
		getCoreArray()[address.getArgumentValue()] = instruction;
	}
}
