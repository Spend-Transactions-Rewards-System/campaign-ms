package sg.edu.smu.cs301.group3.campaignms.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Value("${secret_key}")
    private String secretKey;
    @Value("${access_key}")
    private String accessKey;
    @Value("${aws_region}")
    private String awsRegion;

    public AWSStaticCredentialsProvider getAWSCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                accessKey, secretKey
        ));
    }

    @Bean
    public AmazonSimpleEmailService getAWSSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(getAWSCredentials())
                .withRegion(awsRegion).build();
    }

}
