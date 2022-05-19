package rs.bg.ac.etf.kdp.vd180005.purgers;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dobrosav Vlaskovic
 */
public class Purger extends Thread {
	
	private static Semaphore mutex;
	private static PurgeRequestHandler currentPurgeRequestHandler;

	public Purger() {
		Purger.mutex = new Semaphore(1);
		currentPurgeRequestHandler = null;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// [Critical] Blocks until a request is taken
				mutex.acquire();
				if (null == currentPurgeRequestHandler) {
					currentPurgeRequestHandler = PurgeRequestHandler.purgeQueue.take();
				}
				mutex.release();

				// Send delete request
				currentPurgeRequestHandler.sendNextDeleteRequest();

				// [Critical] Last request resets currentPurgeRequestHandler
				mutex.acquire();
				if (currentPurgeRequestHandler.isFinished()) {
					currentPurgeRequestHandler = null;
				}
				mutex.release();

			} catch (InterruptedException ex) {
				Logger.getLogger(Purger.class.getName()).log(Level.SEVERE, null, ex);
				return;
			}
		}
	}
}
