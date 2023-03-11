package sg.edu.smu.cs301.group3.campaignms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.sql.Date;
import java.util.List;

public class UpdateCampaignJob extends QuartzJobBean {
    private final CampaignsRepository campaignsRepository;

    public UpdateCampaignJob(CampaignsRepository campaignsRepository) {
        this.campaignsRepository = campaignsRepository;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        activateCampaign24Hours();
    }

    private void activateCampaign24Hours() {
        Date curDate = new Date(System.currentTimeMillis());
        List<Campaign> campaigns = campaignsRepository
                .getCampaignsByStartDateBeforeAndEndDateAfter(curDate.toString(), curDate.toString()).stream().peek(campaign -> campaign.setActive(true)).toList();
        campaignsRepository.saveAll(campaigns);
    }
}
