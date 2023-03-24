package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.RewardBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;

@Service
public class SpendService {

    public RewardBean convertToReward(SpendBean spendBean){
        RewardBean reward = RewardBean.builder().build();
        /*
        Need logic to convert spendObject to reward
         */
        return reward;
    }
}
