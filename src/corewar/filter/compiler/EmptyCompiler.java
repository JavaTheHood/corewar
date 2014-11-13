package corewar.filter.compiler;

import java.io.*;

public class EmptyCompiler implements CompilerCore
{
    public boolean compile(Reader i, Writer o) throws IOException
    {
        BufferedReader b = new BufferedReader(i);
        PrintWriter p = new PrintWriter(o);
        for(String l = b.readLine(); l != null; l = b.readLine())
            p.println("000000000000");
        return true;
    }

}
