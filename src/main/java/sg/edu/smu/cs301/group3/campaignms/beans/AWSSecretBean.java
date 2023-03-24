package sg.edu.smu.cs301.group3.campaignms.beans;

import lombok.Data;

@Data
public class AWSSecretBean {
    private String username;
    private String password;
    private String host;
    private String engine;
    private String port;
    private String dbInstanceIdentifier;

}
