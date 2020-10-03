package cn.mrxiao.graduation;

import cn.mrxiao.graduation.beans.Orders;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.text.SimpleDateFormat;

@EnableRedisHttpSession
@EnableAsync
@EnableScheduling//开基于注解的定时任务
@SpringBootApplication
@EnableCaching //开启缓存
@MapperScan("cn.mrxiao.graduation.mapper") //使用MapperScan批量扫描所有的Mapper接口
public class GraduationProjectApplication {
	private static final Logger logger=LoggerFactory.getLogger(GraduationProjectApplication.class);
	public static void main(String[] args)
	{
		logger.info("Yes,my god.App start...");
		SpringApplication.run(GraduationProjectApplication.class, args);
	}
	//将日志记录器加入到容器中
	@Bean
	public Logger getLogger(){
		return LoggerFactory.getLogger(getClass());
	}

	@Bean
	public Orders getOrder(){return new Orders();}

	@Bean
	public SimpleMailMessage getMessage(){
		return new SimpleMailMessage();
	}
	@Bean
	public SimpleDateFormat getDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}


	@Bean(name = "applicationTaskExecutor")
	public AsyncTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(40);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(10);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		executor.setAllowCoreThreadTimeOut(true);
		executor.setKeepAliveSeconds(60);
		executor.setThreadNamePrefix("task-");
		return executor;
	}

}
