/**  
 * 
 * @Title:  TaskManager.java   
 * @Package com.bicon.botu.tools   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 123774135@qq.com     
 * @date:   2018年9月6日 上午10:00:16   
 * @version V1.0 
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 
 */  
package com.bicon.botu.tools;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.bicon.botu.job.ESNlpJob;

/**   
 * @ClassName:  TaskManager   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 123774135@qq.com 
 * @date:   2018年9月6日 上午10:00:16   
 *     
 * @Copyright: 2018 
 * 
 */
public class TaskManager {

	
	 private final static String JOB_GROUP_NAME = "QUARTZ_JOBGROUP_NAME";//任务组
	private static final String TRIGGER_NAME = "ES_TRIGGER";
	
	//private static final String CORN_EXPRESS = "0 0 12 * * ?";
	
	private static final String CORN_EXPRESS = "0/5 * * * * ? ";
	
	public static Scheduler builderTask() {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			return scheduler;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void start() {
		Scheduler scheduler = builderTask();
		if(null == scheduler) {
			throw new NullPointerException("scheduler对象不能为空");
		}
		
		try {
			scheduler.scheduleJob(builderJobDetail(), builderTrigger());
			scheduler.start();
			System.out.println("定时任务已经启动起来!");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * withMisfireHandlingInstructionDoNothing 
            ——不触发立即执行 
            ——等待下次Cron触发频率到达时刻开始按照Cron频率依次执行 
 
            withMisfireHandlingInstructionIgnoreMisfires 
            ——以错过的第一个频率时间立刻开始执行 
            ——重做错过的所有频率周期后 
            ——当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行 
 
            withMisfireHandlingInstructionFireAndProceed 
            ——以当前时间为触发频率立刻触发一次执行 
            ——然后按照Cron频率依次执行 
	 * @Title: builderTrigger   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @return      
	 * Trigger      
	 * @throws
	 */
	public static Trigger  builderTrigger() {
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, JOB_GROUP_NAME)//给触发器起一个名字和组名
				
                .startNow()//立即执行
                .withSchedule(
                		CronScheduleBuilder.cronSchedule(CORN_EXPRESS).withMisfireHandlingInstructionIgnoreMisfires()//时间间隔  单位：秒一直执行
                )
                .build();//产生触发器

     return trigger;

	}
	
	public static JobDetail builderJobDetail() {
		  JobDetail jobDetail = JobBuilder.newJob(ESNlpJob.class).storeDurably().withIdentity(JobKey.jobKey("ES分词更新工作", JOB_GROUP_NAME)).build();
		  return jobDetail;
	}
	
	public static void main(String[] args) {
		TaskManager.start();
	}
}
