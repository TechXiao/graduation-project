package cn.mrxiao.graduation.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfig {
	
	@ConfigurationProperties(prefix="spring.datasource")
	@Bean
	public DataSource druid() {
		return new DruidDataSource();
	}
	
	//配置Druid的监控
	//1、配置一个管理后天的Servlet
	@Bean
	public ServletRegistrationBean<StatViewServlet> statViewServlet(){ //主要实现web监控的配置处理
		//表示进行druid监控的配置处理操作
		ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),"/druid/*");
		Map<String,String> initParams=new HashMap<String, String>();
		initParams.put("loginUsername","admin"); //用户名
		initParams.put("loginPassword","123456"); //密码
		initParams.put("allow","192.168.43.207");//设置允许谁访问，默认就是允许所有访问
		initParams.put("resetEnable","false");//设置是否可以重置数据源
		bean.setInitParameters(initParams);
		return bean;
	}
	//2、配置一个web监控filter
	@Bean
	public FilterRegistrationBean<WebStatFilter> webStatFilter(){
		FilterRegistrationBean<WebStatFilter> bean=new FilterRegistrationBean<WebStatFilter>();
		bean.setFilter(new WebStatFilter());
		
		Map<String,String> initParams=new HashMap<String, String>();
		bean.setUrlPatterns(Arrays.asList("/*"));//设置所有请求进行监控处理
		initParams.put("exclusions","*.js,*.css,*.html,/druid/*");//排除

		bean.setInitParameters(initParams);

		return bean;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
