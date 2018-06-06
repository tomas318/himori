package Utility;

public class handledThread extends Thread {

	private ThreadHandler handler;
	private static IDTagger threadID = new IDTagger(1);

	public handledThread(ThreadHandler handler) {
		super(handler, "handledThread: " + threadID.nextID());
		this.handler = handler;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			Runnable task = null;
			try {
				task = handler.getTask();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (task == null) {
				return;
			} else {
				try {
					task.run();
				} catch (Throwable t) {
					handler.uncaughtException(this, t);
				}
			}
		}
	}

}
