package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.model.Mcc;
import sg.edu.smu.cs301.group3.campaignms.model.MccExclusion;
import sg.edu.smu.cs301.group3.campaignms.repository.MccExclusionRepository;

import java.util.*;

@Service
public class MccExclusionService {

    @Autowired
    private MccExclusionRepository mccExclusionRepository;

    public List<MccExclusion> getAllMccExclusion(){
        return mccExclusionRepository.findAll();
    }

    public Set<Integer> getMccs(String cardType){
        Set<Integer> toReturn = new HashSet<>();
        List<MccExclusion> list = getAllMccExclusion();
        for(MccExclusion mccExclusion : list){
            if(mccExclusion.getCardType().getName().equalsIgnoreCase(cardType)){
                toReturn.add(mccExclusion.getMcc().getMcc());
            }
        }

        return toReturn;
    }

    public Boolean isExcluded(SpendBean spendBean){
        Set<Integer> mccExclusions = getMccs(spendBean.getCardType());
        if(mccExclusions.isEmpty()){
            return false;
        }
        return mccExclusions.contains(spendBean.getMcc());
    }
}
