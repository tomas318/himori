package Utility;

import java.util.LinkedList;
import java.util.List;

public class ThreadHandler extends ThreadGroup {

	private static IDTagger threadID = new IDTagger(1);
	private List<Runnable> taskList;
	private boolean isRunning;
	private int ID;

	public ThreadHandler(int numberofThreads) {
		super("ThreadHandler: " + threadID.nextID());
		this.ID = threadID.getCurrentID();
		setDaemon(true); // exit threadhandler when main thread exits, if set to false would waste extra memory + processing power
		this.taskList = new LinkedList<Runnable>();
		isRunning = true;
		for (int i = 0; i < numberofThreads; i++) {
			new handledThread(this).start();
		}
	}
	
	public synchronized void runTask(Runnable task) {
		if(!isRunning) {
			throw new IllegalStateException("ThreadHandler #" + ID + " is not running.");
		}if (task != null) {
			taskList.add(task);
			notify();
		}
	}
	
	public synchronized void stopHandler() {
		if(!isRunning) {
			return;
		}else {
			isRunning = false;
			taskList.clear();
			interrupt();
		}
	}
	
	public void joinHandler() {
		synchronized(this) {
			isRunning = false;
			notifyAll();
		}
		Thread[] Threads = new Thread[activeCount()];
		int count = enumerate(Threads);
		for (int i = 0; i < count; i++) {
			try {
				Threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskList.size() == 0) {
			if (!isRunning) {
				return null;
			}
			wait();
		}
		return taskList.remove(0);
	}

}
