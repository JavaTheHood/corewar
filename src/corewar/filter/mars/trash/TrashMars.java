/* (C) 2011, R. Schiedermeier, rs@cs.hm.edu
 * Praktikum Softwareentwicklung II, SS2011
 * Fakultät 07 für Informatik und Mathematik, Hochschule München
 *   ____
 *  / ___|___  _ __ _____      ____ _ _ __
 * | |   / _ \| '__/ _ \ \ /\ / / _` | '__|
 * | |__| (_) | | |  __/\ V  V / (_| | |
 *  \____\___/|_|  \___| \_/\_/ \__,_|_|
 *
 * Sun Microsystems Inc. Java 1.6.0_24, Linux i386 2.6.32.21
 * tura (Intel Atom CPU N270/1600 MHz, 2 Cores, 2048 MB RAM)
 */
package corewar.filter.mars.trash;

import corewar.filter.mars.MarsCore;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/** Grottenschlechter Anfang eines MARS.
 * Einschränkungen:
 * - Kann nur ein Programm ausführen.
 * - Kennt nur die Kommandos dat, mov, add, jmp.
 * - Liefert immer Unentschieden.
 * @author Reinhard Schiedermeier, rs@cs.hm.edu
 * @version $Id: TrashMars.java 17 2011-03-20 09:11:44Z schiedi $
 */
public class TrashMars implements MarsCore
{
    public void runBattle(Writer p, Reader... rc) throws IOException
    {
        // Core initialisieren
        final long[] c = new long[4000];
        p.write(String.format("I%d%04d%06d%04d%012d%n", 0, 3999, 399999, 3999, 0));

        // Programm laden
        Reader r = rc[0];
        int ip = 0;
        int chr = r.read();
        while(chr >= 0)
        {
            if(!Character.isWhitespace(chr))
            {
                String str = "" + (char)chr + (char)r.read();
                int cm = Integer.parseInt(str);
                int sm = r.read() - 48;
                int s = Integer.parseInt("" + (char)r.read() + (char)r.read() + (char)r.read() + (char)r.read());
                int dm = r.read() - 48;
                int d = Integer.parseInt("" + (char)r.read() + (char)r.read() + (char)r.read() + (char)r.read());
                long in = cm;
                in = sm + 10 * in;
                in = s + 10000 * in;
                in = dm + 10 * in;
                in = d + 10000 * in;
                p.write(String.format("L%d%04d%06d%04d%012d%n", 0, 0, 0, ip, in));
                c[ip++] = in;
            }
            chr = r.read();
        }
        r.close();

        // Programm starten
        ip = 0;
        int cl = 0;
        boolean a = true;
        while(a && cl < 40)
        {
            long in = c[ip];
            int d = (int)(in % 10000);
            int dm = (int)(in / 10000 % 10);
            int s = (int)(in / 100000 % 10000);
            int sm = (int)(in / 1000000000L % 10);
            int cd = (int)(in / 10000000000L);
            long sl;
            int di;
            p.write(String.format("X%d%04d%06d%04d%012d%n", 0, 0, cl, ip, in));
            switch(cd)
            {
                case 0: // dat
                    a = false;
                    p.write(String.format("K%d%04d%06d%04d%012d%n", 0, 0, 0, 0, 0));
                    break;
                case 1:
                    sl = getContent(c, ip, s, sm);
                    di = getAddress(c, ip, d, dm);
                    p.write(String.format("M%d%04d%06d%04d%012d%n", 0, 0, 0, di, sl));
                    c[di] = sl;
                    ip = mod(ip + 1);
                    break;
                case 2:
                    sl = getContent(c, ip, s, sm);
                    di = getAddress(c, ip, d, dm);  // Fehlerhaft!
                    sl = mod(sl + (int)c[di]);
                    p.write(String.format("M%d%04d%06d%04d%012d%n", 0, 0, 0, di, sl));
                    c[di] = sl;
                    ip = mod(ip + 1);
                    break;
                case 3:
                    di = getAddress(c, ip, d, dm);
                    ip = di;
                    break;
                default:
                    assert false: "unknown command - this cannot happen!";
            }
            cl++;
        }
        p.write(String.format("W%d%04d%06d%04d%012d%n", 2, 0, 0, 0, 0));
    }

    private static long getContent(long[] c, int ip, long x, int m)
    {
        if(m > 1)
            x = c[mod(x + ip)];
        if(m > 0)
            x = c[mod(x + ip)];
        return x;
    }

    private static int getAddress(long[] c, int ip, long x, int m)
    {
        if(m > 1)
            x = (int)c[mod(x + ip)];
        return mod(x + ip);
    }

    private static int mod(long x)
    {
        x %= 4000;
        return (int)(x < 0 ? x + 4000 : x);
    }

}
