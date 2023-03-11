package sg.edu.smu.cs301.group3.campaignms.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.util.Date;

@Transactional
@Service
public class SchedulerService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private CampaignsRepository campaignsRepository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JobSchedulerCreator scheduleCreator;

    private void scheduleJob() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(UpdateCampaignJob.class).build();
            Trigger trigger = scheduleCreator.createCronTrigger("Update campaign",
                    new Date(System.currentTimeMillis()), "0 0 * * *", SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
            if (!scheduler.checkExists(jobDetail.getKey())) {
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
