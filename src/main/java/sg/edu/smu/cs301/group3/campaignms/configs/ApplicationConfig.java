package sg.edu.smu.cs301.group3.campaignms.configs;


import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sg.edu.smu.cs301.group3.campaignms.beans.AWSSecretBean;

import javax.sql.DataSource;

//@Configuration
public class ApplicationConfig {

    Gson gson = new Gson();

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        AWSSecretBean secrets = getDatabaseCreds();
        return DataSourceBuilder
                .create()
                .url(dbUrl)
                .username(secrets.getUsername())
                .password(secrets.getPassword())
                .build();
    }

    private AWSSecretBean getDatabaseCreds() {

        String secretName = "database_name";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();

        String secret;
        GetSecretValueRequest getSecretValueRequest =new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            return gson.fromJson(secret, AWSSecretBean.class);
        }

        return null;
    }
}
