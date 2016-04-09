package restful.task;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class ScheduleTest implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		},new Trigger(){

			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// TODO Auto-generated method stub
				return null;
			}});

	}

}
