package sg.edu.smu.cs301.group3.campaignms.configs;

import io.awspring.cloud.autoconfigure.sqs.SqsProperties;
import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsOperations;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Import(SqsBootstrapConfiguration.class)
@Configuration
public class SQSConfig {

    //Some possible implementation if required any customization for SQS
//    @Bean
//    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
//        return SqsMessageListenerContainerFactory
//                .builder()
//                .sqsAsyncClient(sqsAsyncClient())
//                .build();
//    }
//
//    @Bean
//    public SqsProperties.Listener listener() {
//        return new SqsProperties.Listener();
//    }


    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder().build();
    }

    @Primary
    @Bean
    public SqsOperations sqsOperations() {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient())
                .configureDefaultConverter(sqsMessagingMessageConverter -> {
                    sqsMessagingMessageConverter.setPayloadTypeHeader("my-dto-type-header");
                })
                .buildSyncTemplate();
    }



}
