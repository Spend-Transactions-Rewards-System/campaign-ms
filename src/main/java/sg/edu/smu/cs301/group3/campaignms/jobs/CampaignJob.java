package sg.edu.smu.cs301.group3.campaignms.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

@Component
public class CampaignJob implements Job {
    @Autowired
    private CampaignsRepository campaignsRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        setCampaignStatus(jobDataMap);
    }

    private void setCampaignStatus(JobDataMap jobDataMap) {
        int campaignId = jobDataMap.getInt("campaignId");
        boolean status = jobDataMap.getBoolean("status");
        Campaign campaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        campaign.setActive(status);
        campaignsRepository.save(campaign);
    }
}
