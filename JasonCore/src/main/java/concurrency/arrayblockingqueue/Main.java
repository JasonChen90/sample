package concurrency.arrayblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;

public class Main {

	@Test
	public void testArrayBlockingQueue(){
		try {
			System.out.println("Test starting....");
			ArrayBlockingQueue<Record> queue = new ArrayBlockingQueue<>(10);
			AbqProducer abqProducer = new AbqProducer();
			abqProducer.setQueue(queue);
			AbqConsumer abqConsumer = new AbqConsumer();
			abqConsumer.setQueue(queue);
			
			//通过控制producer和consumer的执行速度来模拟不同场景
			Thread producer = new Thread(abqProducer);
			Thread consumer = new Thread(abqConsumer);
			producer.start();
			consumer.start();
			
			System.out.println("Main thread sleeping....");
			Thread.sleep(60000);
			System.out.println("Test ending....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testArrayBlockingQueueFairness(){
		try {
			System.out.println("Test starting....");
			ArrayBlockingQueue<Record> queue = new ArrayBlockingQueue<>(10);
			AbqProducer abqProducer = new AbqProducer();
			abqProducer.setQueue(queue);
			AbqConsumer abqConsumer = new AbqConsumer();
			abqConsumer.setQueue(queue);
			
			//通过控制producer和consumer的执行速度来模拟不同场景
			Thread producer = new Thread(abqProducer);
			Thread consumer = new Thread(abqConsumer);
			producer.start();
			consumer.start();
			
			System.out.println("Main thread sleeping....");
			Thread.sleep(60000);
			System.out.println("Test ending....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
