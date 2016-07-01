package thread.sync;

import org.junit.Test;

import thread.sync.TaskExecuter;

public class Main {

	@Test
	public void TestSync(){
		final TaskExecuter taskExecuter = new TaskExecuter();
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				taskExecuter.exec();
			}
		};
		
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		thread1.start();
		thread2.start();
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
