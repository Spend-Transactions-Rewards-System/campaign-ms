package sg.edu.smu.cs301.group3.campaignms.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.Customer;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.CustomerRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.NotificationsRepository;
import sg.edu.smu.cs301.group3.campaignms.service.EmailService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CampaignJob implements Job {
    @Autowired
    private CampaignsRepository campaignsRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        setCampaignStatus(jobDataMap);
    }

    private void setCampaignStatus(JobDataMap jobDataMap) {
        long campaignId = jobDataMap.getLong("campaignId");
        boolean status = jobDataMap.getBoolean("status");
        Campaign campaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        campaign.setActive(status);
        campaignsRepository.save(campaign);
        sendNotificationEmail(campaign);
    }

    private void sendNotificationEmail(Campaign campaign) {
        List<String> customerEmailList = customerRepository.findCustomersByCardType(campaign.getCardType())
                .stream().map(Customer::getEmail).toList();
        Notification notification = notificationsRepository.getNotificationsByCampaign(campaign).get(0);
        customerEmailList.forEach(email -> emailService.send(notification, email));
    }
}
