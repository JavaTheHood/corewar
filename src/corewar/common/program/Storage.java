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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import corewar.common.instruction.Command;
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
 * This is a storage for Code. Every RedcodeCommand has codes, what is executed
 * during a call of task.step(). The right code for evey command is stored in
 * this 'mini-databank'.
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter -
 *         janek.schoenwetter@yahoo.com
 */
public final class Storage {


	/**
	 * A Map in witch commands(key) and code(value) are stored.
	 */
	private static Map<Command, CoreCode> coreCodeStorage;

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
		coreCodeStorage = Collections.unmodifiableMap(tmpStorage);
	}

	/**
	 * Blocked private default Constructor with assertion.
	 */
	private Storage() {
		assert false : "Utility class without instances!";
	}

	/**
	 * A getter for the storage.
	 * @return the storage
	 */
	public static Map<Command, CoreCode> getStorage() {
		return coreCodeStorage;
	}

}
