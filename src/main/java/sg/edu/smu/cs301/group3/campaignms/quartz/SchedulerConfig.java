package sg.edu.smu.cs301.group3.campaignms.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class SchedulerConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {

        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        Properties properties = getQuartzProperties();

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(properties);
        factory.setJobFactory(jobFactory);
        return factory;
    }

    private Properties getQuartzProperties() {
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "spring-boot-quartz");
        props.put("org.quartz.scheduler.instanceId", "AUTO");
        props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        props.put("org.quartz.threadPool.threadCount", 25);
        props.put("org.quartz.threadPool.threadPriority", 5);
        props.put("org.quartz.jobStore.isClustered", "true");
        props.put("org.quartz.jobStore.clusterCheckinInterval", 1000);
        return props;
    }

}
