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

import corewar.common.constants.Constants;
import corewar.common.core.Core;
import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;
import corewar.pipe.eventlog.Event;
import corewar.pipe.eventlog.EventType;

/**
 * Die Klasse stellt die Tasks eines Programms dar. Die Programme können mehrere Tasks haben.
 * Wenn ein Task eliminiert wird, leben die weitern Tasks weiter. Das Programm is erst dann
 * nicht mehr lebensfähig, wenn kein Task mehr übrig ist.
 * The Task of a RedCode Program. A Program can have several tasks, what are
 * executed in a specific order during the runtime. The Program is dead when no task left.
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 * @version 1.0
 */
public class Task implements Comparable<Task> {

	/**
	 * Die Adresse des Tasks im MemoryArrayCore
	 * The address on witch the task is stored in the MemoryArrayCore.
	 */
	private Value address;

	/**
	 * Der Index des Tasks. Beschreibt die Reihenfolge der Tasks.
	 * The Index of the Task - the index describes when the task was created.
	 */
	private final int index;

	/**
	 * Gibt an, ob der Task noch lebt.
	 * Flag value for if the task is still alive - executable.
	 */
	private boolean alive;

	/**Programm welches den Task  erzeugt hat.
	 * The Program what created the task.
	 */
	private final Program parent;

	/**
	 * Custom constructor for a task. Therefore the logic dictates that a task
	 * is always alive, when it is created. So the field 'alive' is default
	 * initialized true.
	 * @param address
	 *            The address, on with the task will start.
	 * @param index
	 *            The index of the Task. The index can never be bigger than the
	 *            size of the MemoryArrayCore
	 * @param program
	 *            the program, what created this task.
	 */
	public Task(final Value address, final int index, final Program program) {
		this.address = address;
		this.index = index;
		alive = true;
		parent = program;
		assert index < Constants.CORE_SIZE : "Index is in the right scope!";
	}

	/**
	 * Getter for the alive of the Task. Only alive tasks can be executed.
	 * @return the FlagValue alive.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Getter for the Parent program.
	 * @return the parent Program what created the task.
	 */
	public Program getParent() {
		return parent;
	}

	/**
	 * Setter for the alive value. This setter is private and only used by the
	 * Method kill() - so no tasks could be reanimated. There is also an
	 * assertion what checks that there no set true instructions.
	 * @param alive
	 *            the value to set - only false allowed!
	 */
	private void setAlive(final boolean alive) {
		this.alive = alive;
		assert !alive : "only set alive false!";
	}

	/**
	 * Getter for the address of the task.
	 * @return The address value of the task.
	 */
	public Value getAddress() {
		return address;
	}

	/**
	 * Setter for the address of the Task. Is used by most of the SetMemory
	 * Codes and during every call of step(...).
	 * @param address
	 *            The address value to set.
	 */
	public void setAddress(final Value address) {
		this.address = address;
	}

	/**
	 * Getter for the index of the Task.
	 * @return the Index of the task.
	 */
	public int getIndex() {
		return index;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getIndex() + getParent().getIndex();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result = true;
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			result = false;
		final Task other = (Task) obj;
		if (!getAddress().equals(other.getAddress()))
			result = false;
		if (isAlive() != other.isAlive() || getIndex() != other.getIndex()
				|| !getParent().equals(other.getParent()))
			result = false;
		return result;
	}

	/**
	 * Kills a task. This method sets the alive of the task false and writes a
	 * kill event on a writer.
	 * @param print
	 *            A writer on witch the kill event is written.
	 * @throws SyntaxErrorException
	 *             actually impossible - caused by the default constructor of
	 *             Instruction.
	 * @throws IOException
	 *             could be thrown from the writer.
	 */
	public void kill(final Writer print) throws IOException,
			SyntaxErrorException {
		setAlive(false);
		print.append(new Event(EventType.Kill,
				getParent().getIndex(), getIndex(), 0, new Value(0),
 new Instruction()).toString());
	}

	/**
	 * Executes the next Instruction from this Task. This method is the 'heart'
	 * of this class and very important for the whole corewar. It gets and
	 * executes the right instruction, plus writes execute and setMem events on
	 * a writer.
	 * @param core
	 *            The MemoryArrayCore on witch the task should execute.
	 * @param cycles
	 *            The actual cycle, or round of the corewar.
	 * @param print
	 *            the Writer on witch the events are written
	 * @throws IOException
	 *             May be caused by the writer.
	 * @throws SyntaxErrorException
	 *             Actually not possibly - relict of the compiler.
	 */
	public void step(final Core core, final int cycles, final Writer print)
			throws IOException, SyntaxErrorException {
		final Instruction instruction = core.getInstrucion(getAddress());
		Storage.getStorage().get(instruction.getCommand())
				.execute(core, this, cycles, instruction, print);
		setAddress(new Value(getAddress().getArgumentValue() + 1));
	}

	@Override
	public int compareTo(final Task toCmp) {
		return getIndex() - toCmp.getIndex();
	}
}
