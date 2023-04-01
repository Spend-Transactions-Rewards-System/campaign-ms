package sg.edu.smu.cs301.group3.campaignms.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.model.Mcc;
import sg.edu.smu.cs301.group3.campaignms.repository.MccRepository;

import java.util.*;

@Service
public class MccService {

    @Autowired
    private MccRepository mccRepository;

    public List<Mcc> getAllMcc(){
        return mccRepository.findAll();
    }
}
