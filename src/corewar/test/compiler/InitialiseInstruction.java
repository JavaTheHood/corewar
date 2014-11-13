package corewar.test.compiler;

import corewar.common.exceptions.SyntaxErrorException;
import corewar.pipe.decimalcode.Parser;

public class InitialiseInstruction {
	public static void main(final String[] args) throws SyntaxErrorException {

		final char[] decimalcode = "011035420265".toCharArray();
		System.out.println(String.valueOf(decimalcode));
		System.out.println(Parser.toInstruction(decimalcode));
	}
}
