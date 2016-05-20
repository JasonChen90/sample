package concurrency.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

public class AbqConsumer implements Runnable {

	private ArrayBlockingQueue<Record> queue;
	
	public ArrayBlockingQueue<Record> getQueue() {
		return queue;
	}
	public void setQueue(ArrayBlockingQueue<Record> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(5000);
				Record record = queue.take();
				//�������У����޷�take��element�����������治ִ��
				System.out.println("Consume record with record id: " + record.getRecordId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
