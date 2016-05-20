package concurrency.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

public class AbqProducer implements Runnable {

	private ArrayBlockingQueue<Record> queue;
	
	public ArrayBlockingQueue<Record> getQueue() {
		return queue;
	}

	public void setQueue(ArrayBlockingQueue<Record> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		int recordId = 0;
		while(true){
			try {
				Thread.sleep(1000);
				Record record = new Record();
				record.setRecordId(recordId++);
				System.out.println("Produce record with record id: " + record.getRecordId());
				queue.put(record);
				//�������У����޷�take element�����������治ִ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
