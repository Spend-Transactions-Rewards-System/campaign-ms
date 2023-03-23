package sg.edu.smu.cs301.group3.campaignms.service;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.repository.NotificationsRepository;


@Service
public class EmailService {
    @Autowired
    private NotificationsRepository notificationsRepository;

    private final static String FROM_EMAIL = "g1t3cs@gmail.com";
    private final static String FROM_NAME = "G1T3 tEAM";

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    public SdkHttpMetadata send(Campaign campaign, String userEmail) {
        Notification notification = notificationsRepository.getNotificationsByCampaign(campaign).get(0);

        SendEmailRequest request = new SendEmailRequest()
                .withMessage(new Message()
                        .withBody(new Body().withText(new Content(notification.getMessage())))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(notification.getTitle())))
                .withSource(String.format("%s <%s>", FROM_NAME, FROM_EMAIL))
                .withDestination(new Destination().withToAddresses(userEmail));

        SendEmailResult response = amazonSimpleEmailService.sendEmail(request);

        return response.getSdkHttpMetadata();
    }

}
