package thread.sync;

public class TaskExecuter {

	public TaskExecuter(){
		this.subTask = new SubTask();
	}
	
	private SubTask subTask;
	
	public void exec(){
		System.out.println("start TaskExecuter.exec(" + "Thread.currentThread().Id" + Thread.currentThread().getName() + ")");
		synchronized (subTask) {
			subTask.exec();
		}
		System.out.println("end TaskExecuter.exec(" + "Thread.currentThread().Id" + Thread.currentThread().getName() + ")");
	}

	private class SubTask{
		public void exec(){
			System.out.println("SubTask.exec(" + "Thread.currentThread().Id" + Thread.currentThread().getName() + ")");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
