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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import corewar.filter.ConsoleFilter;

/**
 * @author Roman Podolski - r.podolski@web.de, Jannek Schoenwetter - janek.schoenwetter@yahoo.com
 *
 */
public class FileViewer {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {

		if(args.length != 3)
			throw new IllegalArgumentException("\nrequired args are: classname-viewer fight.log fight.view");

		final ConsoleFilter consoleFilter = new ConsoleFilter();
		int arg = 0;
		final ViewerCore viewer = (ViewerCore)consoleFilter.makeFilterObject(args[arg++]);
		final Reader source = new FileReader(args[arg++]);
		final Writer destination = new FileWriter(args[arg++]);
		viewer.present(source, destination);

		source.close();
		destination.flush();
		destination.close();
		consoleFilter.close();


	}

}
