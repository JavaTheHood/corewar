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
/**Redcode-Compiler, works as filter.
 * Reads and writes sourcecode from the standart I/O system.
 * @author Janek Schoenwetter - schoenwe@fhm.edu, Roman Podolski - r.podolski@web.de
 * @version 1.0
 */
package corewar.filter.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import corewar.common.exceptions.SyntaxErrorException;
import corewar.common.instruction.Instruction;
import corewar.pipe.decimalcode.Decimalcode;
import corewar.pipe.sourcecode.InstructionParser;

/**
 * RedcodeCompiler.
 *
 * @author Janek Schoenwetter, Roman Podolski
 * @version 1.0
 */
public class RedCodeCompiler implements CompilerCore {
	/**
	 * Reads source code from a reader and writes decimal-RedCode on a writer.
	 * @param input
	 *            Reader, open to read Redcode source code. Reads to the end. Is
	 *            not closed.
	 * @param output
	 *            Writer, open to output decimal-Redcode. Is not closed.
	 * @return true = Compiler was successful; false = syntax error.
	 * @throws IOException
	 *             I/O-Error wile reading source code or writing decimal code.
	 */
	public boolean compile(final Reader input, final Writer output)
			throws IOException {
		final BufferedReader buffered = new BufferedReader(input);
		final PrintWriter print = new PrintWriter(output);
		boolean result = true;
		int lineCounter = 0;
		final List<Instruction> instructionList = new ArrayList<Instruction>();
		for (String line = buffered.readLine(); line != null; line = buffered.readLine()) {
			lineCounter++;
			try {
				instructionList.add(InstructionParser.parseInstruction(line));
			} catch (final SyntaxErrorException e) {
				System.err.println("compile failed, error in line "
						+ lineCounter + ".\n" + e.getError() + "\n"
						+ e.getClass());
				result = false;
			}
		}
		while (instructionList.contains(null))
			instructionList.remove(null);

		for (final Instruction instruction : instructionList)
			print.append(Decimalcode.encode(instruction));

		return result;
	}
}
