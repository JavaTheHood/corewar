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
package corewar.filter.viewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import corewar.common.instruction.Instruction;
import corewar.pipe.decimalcode.Parser;

/**
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter - janek.schoenwetter@yahoo.com
 *
 */
public class Telescope implements ViewerCore {

	/* (non-Javadoc)
	 * @see corewar.filter.viewer.ViewerCore#present(java.io.Reader, java.io.Writer)
	 */
	@Override
	public void present(final Reader protocolReader, final Writer copyWriter)
			throws IOException {

		final BufferedReader input = new BufferedReader(protocolReader);
		final PrintWriter view = new PrintWriter(copyWriter);

		String line = input.readLine();
		final int warriors = Integer.parseInt(line.substring(1, 2))+1;

		while(line != null){

			final int program = Integer.parseInt(line.substring(1, 2))+1;
			final int task = Integer.parseInt(line.substring(2, 6))+1;
			final int cycle = Integer.parseInt(line.substring(6, 11))+1;
			final int address = Integer.parseInt(line.substring(12, 16));
			final Instruction instruction = Parser.toInstruction(line.substring(16, line.length()).toCharArray());

			System.out.println("\n");
			System.out.println(line);
			System.out.println("Program: "+program);
			System.out.println("Task: "+task);
			System.out.println("Cycle: "+cycle);
			System.out.println("Address: "+address);
			System.out.println(instruction);

			switch (line.charAt(0)) {
			case 'I':
				view.append("Simulation started\nCore initialzed\nSize: "+task+"\nMaximum cycles: "+cycle+"\nWarriors: "+warriors+"\n");
				break;

			case 'L':
				view.append("Warrior #"+program+" loaded instruction: "+instruction+"\n");
				break;

			case 'X':
				view.append("Round: "+cycle+" - Warrior #"+program+" executed "+instruction+"\n");
				break;

			case 'M':
				view.append("Memory on address "+address+" resetted to: "+instruction+ "\n");
				break;

			case 'K':
				view.append("Warrior #"+program+" task #"+task+" killed\n");
				break;

			case 'W':
				if(Integer.parseInt(line.substring(1, 2))> warriors)
					view.append("Draw!");
				view.append("Round: "+cycle+" - Warrior #"+program+" winns!");
				break;

			default:
				assert false: "Decimalcode is correct!!";
				break;
			}
		line = input.readLine();
		}
	}

}
