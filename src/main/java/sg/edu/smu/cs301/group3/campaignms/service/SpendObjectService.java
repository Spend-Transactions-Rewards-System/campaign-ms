package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.model.Reward;
import sg.edu.smu.cs301.group3.campaignms.model.SpendObject;

@Service
public class SpendObjectService {

    public Reward convertToReward(SpendObject spendObject){
        Reward reward = Reward.builder().build();
        /*
        Need logic to convert spendObject to reward
         */
        return reward;
    }
}
