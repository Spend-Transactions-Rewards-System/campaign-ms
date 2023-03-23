package sg.edu.smu.cs301.group3.campaignms.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    public AWSStaticCredentialsProvider getAWSCredentials() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                "access_key", "secret_key"
        ));
    }

    @Bean
    public AmazonSimpleEmailService getAWSSimpleEmailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(getAWSCredentials())
                .withRegion("region").build();
    }

}
