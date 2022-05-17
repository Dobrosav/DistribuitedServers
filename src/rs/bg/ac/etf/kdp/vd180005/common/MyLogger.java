package rs.bg.ac.etf.kdp.vd180005.common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class MyLogger {
	public final static Logger logger = Logger.getLogger(MyLogger.class.getName());
	
	// static initializer
	static {
		try {
			FileHandler fh = new FileHandler("server.log", false);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			logger.setLevel(Level.CONFIG);
		} catch (IOException | SecurityException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}
}
