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
package corewar.pipe.eventlog;

import corewar.common.instruction.Instruction;
import corewar.common.instruction.Value;

/**
 * @author Roman Podolski - r.podolski@web.de, Janek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 *
 */
public class Event {

	/**
	 *
	 */
	private final EventType eventType;
	/**
	 *
	 */
	private final int programmIndex;
	/**
	 *
	 */
	private final Instruction instruction;
	/**
	 *
	 */
	private final int threadIndex;
	/**
	 *
	 */
	private final int cycle;
	/**
	 *
	 */
	private Value address;

	/**
	 *
	 * @param eventType
	 * @param programmIndex
	 * @param threadIndex
	 * @param cycle
	 * @param address
	 * @param instruction
	 */
	public Event(final EventType eventType, final int programmIndex,
			final int threadIndex, final int cycle, final Value address,
			final Instruction instruction) {
		this.eventType = eventType;
		this.programmIndex = programmIndex;
		this.threadIndex = threadIndex;
		this.cycle = cycle;
		this.address = address;
		this.instruction = instruction;
	}

	/**
	 * @return the programmIndex
	 */
	int getProgrammIndex() {
		return programmIndex;
	}

	/**
	 * @return the eventType
	 */
	EventType getEventType() {
		return eventType;
	}

	/**
	 * @return the instruction
	 */
	Instruction getInstruction() {
		return instruction;
	}

	/**
	 * @return the threadIndex
	 */
	int getThreadIndex() {
		return threadIndex;
	}

	/**
	 * @return the cycle
	 */
	int getCycle() {
		return cycle;
	}

	/**
	 * @return the address
	 */
	Value getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	void setAddress(final Value address) {
		this.address = address;
	}
}
