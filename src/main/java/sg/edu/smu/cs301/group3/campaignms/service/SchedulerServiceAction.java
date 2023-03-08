package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Scope("singleton")
public class SchedulerServiceAction {
    private final Map<Integer, ScheduledFuture> campaignStartTaskMap;
    private final Map<Integer, ScheduledFuture> campaignEndTaskMap;
    private final CampaignsRepository campaignsRepository;
    private final TaskScheduler taskScheduler;

    public SchedulerServiceAction(CampaignsRepository campaignsRepository) {
        this.campaignsRepository = campaignsRepository;
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.campaignStartTaskMap = initializeScheduleTaskMap(true);
        this.campaignEndTaskMap = initializeScheduleTaskMap(false);
    }

    private Map<Integer, ScheduledFuture> initializeScheduleTaskMap(boolean isStart) {
        List<Campaign> campaignList = campaignsRepository.findByEndDateBefore(new java.sql.Date(System.currentTimeMillis()).toString());

        return campaignList.stream().collect(Collectors.toMap(Campaign::getCampaignId,
                campaign -> createScheduledTaskFuture(campaign, isStart)));
    }

    public void createCampaignScheduler(Campaign campaign) {
        campaignStartTaskMap.put(campaign.getCampaignId(), createScheduledTaskFuture(campaign, true));
        campaignEndTaskMap.put(campaign.getCampaignId(), createScheduledTaskFuture(campaign, false));
    }

    public void editCampaignDate(Campaign campaign) {
        ScheduledFuture startCampaignTask = createScheduledTaskFuture(campaign, true);
        ScheduledFuture endCampaignTask = createScheduledTaskFuture(campaign, false);
        campaignStartTaskMap.put(campaign.getCampaignId(), startCampaignTask);
        campaignEndTaskMap.put(campaign.getCampaignId(), endCampaignTask);
    }

    public void deleteCampaignTask(int campaignId) {
        campaignStartTaskMap.remove(campaignId);
        campaignEndTaskMap.remove(campaignId);
    }

    private ScheduledFuture createScheduledTaskFuture(Campaign campaign, boolean isStart) {
        return taskScheduler.schedule(new CampaignScheduledTask(campaign.getCampaignId()
                , campaignsRepository), isStart ? new Date(campaign.getStartDate()): new Date(campaign.getEndDate()));
    }
}
