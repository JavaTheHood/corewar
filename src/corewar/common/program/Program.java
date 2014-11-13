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
package corewar.common.program;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import corewar.common.core.Core;
import corewar.common.exceptions.IllegalScopeErrorException;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Command;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;
import corewar.common.program.code.AddCode;
import corewar.common.program.code.CmpCode;
import corewar.common.program.code.CoreCode;
import corewar.common.program.code.DatCode;
import corewar.common.program.code.DjzCode;
import corewar.common.program.code.JmpCode;
import corewar.common.program.code.JmzCode;
import corewar.common.program.code.MovCode;
import corewar.common.program.code.SplCode;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public class Program {

	/**
	 *
	 */
	private static Map<Command, CoreCode> storage;

	static {
		final Map<Command, CoreCode> tmpStorage = new EnumMap<Command, CoreCode>(
				Command.class);
		tmpStorage.put(Command.DAT, new DatCode());
		tmpStorage.put(Command.MOV, new MovCode());
		tmpStorage.put(Command.ADD, new AddCode());
		tmpStorage.put(Command.JMP, new JmpCode());
		tmpStorage.put(Command.JMZ, new JmzCode());
		tmpStorage.put(Command.DJZ, new DjzCode());
		tmpStorage.put(Command.CMP, new CmpCode());
		tmpStorage.put(Command.SPL, new SplCode());
		storage = Collections.unmodifiableMap(tmpStorage);
	}
	/**
	 *
	 */
	private Value address;
	/**
	 *
	 */
	private final int index;
	/**
	 *
	 */
	private boolean alive;
	/**
	 * @param addressInstruction
	 * @param index
	 * @param alive
	 */
	public Program(final Value address, final int index) {
		this.address = address;
		this.index = index;
		alive = true;
	}

	/**
	 * @return the storage
	 */
	private static Map<Command, CoreCode> getStorage() {
		return storage;
	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @param alive
	 *            the alive to set
	 */
	public void setAlive(final boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the addressInstruction
	 */
	public Value getAddress() {
		return address;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 *
	 * @param core
	 * @return
	 */
	private Instruction nextInstruction(final Core core) {
		final Instruction instruction = core.getInstrucion(getAddress());
		return instruction;
	}

	/**
	 *
	 * @param newValue
	 */
	private void setAddress(final int newValue) {
		try {
			address = new Value(newValue);
		} catch (final IllegalScopeErrorException e) {
			assert false : "Unreachable Code - scope has been testet and is correct!";
		}
	}

	/**
	 *
	 * @param core
	 * @param cycles
	 * @param print
	 * @return
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */
	public void step(final Core core, final int cycles, final Writer print)
			throws IOException, SyntaxErrorException {
		final Instruction instruction = nextInstruction(core);
		getStorage().get(instruction.getCommand()).execute(core, this, cycles,
				instruction,
				print);
		setAddress(getAddress().getArgumentValue() + 1);
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// Generierter Code - Modivizieren!!!
		final int prime = 31;
		int result = 1;
		result = prime * result + (address == null ? 0 : address.hashCode());
		result = prime * result + (alive ? 1231 : 1237);
		result = prime * result + index;
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result = true;
		if (obj == null || getClass() != obj.getClass())
			result = false;
		final Program other = (Program) obj;
		// Test sinnvoll?
		// Oder einfach false zurück wenn etwas null
		if (address == null) {
			if (other.address != null)
				result = false;
		} else if (!address.equals(other.address) || alive != other.alive
				|| index != other.index)
			result = false;
		return result;
	}
}
