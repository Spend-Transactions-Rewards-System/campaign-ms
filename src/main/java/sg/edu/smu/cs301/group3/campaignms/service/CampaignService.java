package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.jobs.CampaignJob;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
@RequiredArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignsRepository campaignsRepository;

    private final Scheduler scheduler;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private final String cronFormat = "0 0 %d %d *";

    public List<Campaign> getAllCampaign() {
        return campaignsRepository.findAll();
    }

    public List<Campaign> getCampaignByCardId(int id) {
        return campaignsRepository.getCampaignsByCardProgramId(id);
    }

    public Campaign addCampaign(CampaignBean campaignBean) throws Exception {
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        Campaign campaign = Campaign
                .builder()
                .title(campaignBean.getTitle())
                .startDate(new Timestamp(startDate.getTime()))
                .endDate(new Timestamp(endDate.getTime()))
                .mcc(campaignBean.getMcc())
                .minDollarSpent(campaignBean.getMinDollarSpent())
                .rewardRate(campaignBean.getPointsPerDollar())
                .cardProgramId(campaignBean.getCardProgramId())
                .build();
        campaign.setActive(withinCampaignPeriod(campaign));
        createJob(campaign);
        return campaignsRepository.save(campaign);
    }

    public Campaign editCampaign(CampaignBean campaignBean, int campaignId) throws Exception {
        Campaign retrievedCampaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        if (retrievedCampaign == null) {
            throw new Exception("Campaign not found");
        }
        retrievedCampaign.setTitle(campaignBean.getTitle());
        retrievedCampaign.setStartDate(new Timestamp(startDate.getTime()));
        retrievedCampaign.setEndDate(new Timestamp(endDate.getTime()));
        retrievedCampaign.setMcc(campaignBean.getMcc());
        retrievedCampaign.setMinDollarSpent(campaignBean.getMinDollarSpent());
        retrievedCampaign.setRewardRate(campaignBean.getPointsPerDollar());
        retrievedCampaign.setActive(withinCampaignPeriod(retrievedCampaign));
        return campaignsRepository.save(retrievedCampaign);
    }

    public Campaign deleteCampaign(int campaignId) {
        return campaignsRepository.deleteByCampaignId(campaignId);
    }

    private boolean withinCampaignPeriod(Campaign campaign) {
        return campaign.getStartDate().before(new Date(System.currentTimeMillis())) && campaign.getEndDate().after(new Date(System.currentTimeMillis()));
    }

    private void createJob(Campaign campaign) throws SchedulerException {
        JobDataMap startData = new JobDataMap(Map.of(
                "campaignId", campaign.getCampaignId(),
                "status", true));
        JobDataMap endData = new JobDataMap(Map.of(
                "campaignId", campaign.getCampaignId(),
                "status", false));
        JobKey startCampaignJobKey = new JobKey(campaign.getCampaignId() + "-start");
        JobKey endCampaignJobKey = new JobKey(campaign.getCampaignId() + "-end");
        String startCampaignCron = String.format(cronFormat,
                campaign.getStartDate().toLocalDateTime().getMonth().getValue(),
                campaign.getStartDate().toLocalDateTime().getDayOfMonth());
        String endCampaignCron = String.format(cronFormat,
                campaign.getEndDate().toLocalDateTime().getMonth().getValue(),
                campaign.getEndDate().toLocalDateTime().getDayOfMonth());
        Trigger startCampaignTrigger = newTrigger().withSchedule(
                cronSchedule(startCampaignCron)
                        .withMisfireHandlingInstructionFireAndProceed()
                        .inTimeZone(TimeZone.getDefault()))
                .build();
        Trigger endCampaignTrigger = newTrigger().withSchedule(
                        cronSchedule(endCampaignCron)
                                .withMisfireHandlingInstructionFireAndProceed()
                                .inTimeZone(TimeZone.getDefault()))
                .build();
        JobDetail startCampaignJob = newJob(CampaignJob.class).withIdentity(startCampaignJobKey).usingJobData(startData).build();
        JobDetail endCampaignJob = newJob(CampaignJob.class).withIdentity(endCampaignJobKey).usingJobData(endData).build();
        if (!scheduler.checkExists(startCampaignJobKey)) {
            scheduler.scheduleJob(startCampaignJob, startCampaignTrigger);
        }
        if (!scheduler.checkExists(endCampaignJobKey)) {
            scheduler.scheduleJob(endCampaignJob, endCampaignTrigger);
        }
    }
}
