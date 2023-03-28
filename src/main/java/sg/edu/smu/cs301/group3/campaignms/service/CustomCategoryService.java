package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.model.CustomCategory;
import sg.edu.smu.cs301.group3.campaignms.repository.CustomCategoryRepository;

import java.util.List;

@Service
public class CustomCategoryService {

    @Autowired
    private CustomCategoryRepository customCategoryRepository;

    public List<CustomCategory> getCustomCategory(String name, String merchant){
        return customCategoryRepository.findByNameAndMerchant(name, merchant);
    }
}
