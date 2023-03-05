package sg.edu.smu.cs301.group3.campaignms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CampaignMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignMsApplication.class, args);
	}

}
