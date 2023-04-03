package sg.edu.smu.cs301.group3.campaignms.service;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBonusAlertBean;
import sg.edu.smu.cs301.group3.campaignms.constants.Status;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.model.Customer;
import sg.edu.smu.cs301.group3.campaignms.model.Notification;
import sg.edu.smu.cs301.group3.campaignms.model.NotificationLogs;


@Service
@RequiredArgsConstructor
public class EmailService {
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final static String FROM_EMAIL = "g1t3cs@gmail.com";
    private final static String FROM_NAME = "SCIS G1T3";

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final NotificationLogsService notificationLogsService;
    private final CustomerService customerService;

    public SdkHttpMetadata send(Notification notification, String userEmail) {

        SendEmailRequest request = new SendEmailRequest()
                .withMessage(new Message()
                        .withBody(new Body().withText(new Content(notification.getMessage())))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(notification.getTitle())))
                .withSource(String.format("%s <%s>", FROM_NAME, FROM_EMAIL))
                .withDestination(new Destination().withToAddresses(userEmail));

        SendEmailResult response = amazonSimpleEmailService.sendEmail(request);

        logEmailNotification(response);

        return response.getSdkHttpMetadata();
    }

    public void sendCampaignBonusAlertToCustomer(CampaignBonusAlertBean campaignBonusAlertBean) {

        Customer customer = customerService.getCustomerGivenEmail(campaignBonusAlertBean.getEmail());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Dear %s, %n%n", customer.getName()));

        sb.append(String.format("Thank you for using your %s card on %s. %n%n", campaignBonusAlertBean.getCardType(), campaignBonusAlertBean.getSpendDate()));

        sb.append(String.format("We are pleased to update that you have been awarded a bonus of %.2f %s as you have fulfilled our %s ",
                campaignBonusAlertBean.getRewardAmount()
                , new CardType(null, campaignBonusAlertBean.getCardType(), "scis", null).getRewardUnit()
                , campaignBonusAlertBean.getRemarks()));

        sb.append(String.format("on your spend of %s %.2f at %s.", campaignBonusAlertBean.getCurrency(), campaignBonusAlertBean.getAmount(), campaignBonusAlertBean.getMerchant()));

        sb.append("\n\nThank you for choosing SCIS Bank.");

        logger.info("EMAIL SENT: \n" + sb.toString());

        SendEmailRequest request = new SendEmailRequest()
                .withMessage(new Message()
                        .withBody(new Body().withText(new Content(sb.toString())))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData("Campaign Bonus Alert")))
                .withSource(String.format("%s <%s>", FROM_NAME, FROM_EMAIL))
                .withDestination(new Destination().withToAddresses(campaignBonusAlertBean.getEmail()));

        SendEmailResult response = amazonSimpleEmailService.sendEmail(request);

        logEmailNotification(response);

        logger.info("EMAIL SENT with STATUS: " + response.getSdkHttpMetadata().toString());
    }


    private void logEmailNotification(SendEmailResult sendEmailResponse) {
        int statusCode = sendEmailResponse.getSdkHttpMetadata().getHttpStatusCode();
        if (statusCode >= 200 && statusCode < 300) {
            notificationLogsService.createNotificationLogs(NotificationLogs
                    .builder()
                    .status(Status.SUCCESS)
                    .build());
        } else {
            notificationLogsService.createNotificationLogs(NotificationLogs
                        .builder()
                        .status(Status.DROPPED)
                        .errorMessage(sendEmailResponse.getSdkHttpMetadata().toString())
                        .build()
            );
        }
    }



}
