package sg.edu.smu.cs301.group3.campaignms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.RewardBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.model.CustomCategory;
import sg.edu.smu.cs301.group3.campaignms.model.Mcc;
import sg.edu.smu.cs301.group3.campaignms.repository.CardTypeRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.MccExclusionRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.MccRepository;

import java.sql.Timestamp;
import java.util.*;

import static sg.edu.smu.cs301.group3.campaignms.constants.Remarks.BASE;
import static sg.edu.smu.cs301.group3.campaignms.constants.Remarks.NOREWARD;

@Service
public class SpendService {

    private CardTypeRepository cardTypeRepository;

    @Autowired
    private MccExclusionService mccExclusionService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CustomCategoryService customCategoryService;

    private MccExclusionRepository mccExclusionRepository;

    public List<RewardBean> convertToReward(SpendBean spendBean){
        List<RewardBean> list = new ArrayList<>();
        Long cardId = spendBean.getCard_id();
        Optional<CardType> cardType = cardTypeRepository.findById(cardId);
        if(cardType.isEmpty()){
            return null;
        }

        List<Campaign> baseList = new ArrayList<>();
        List<Campaign> categoryList = new ArrayList<>();
        List<Campaign> campaignList = new ArrayList<>();
        List<Campaign> campaigns = cardType.get().getCampaignList();
        for(Campaign campaign : campaigns){
            if(campaign.getTitle().equalsIgnoreCase("base")){
                baseList.add(campaign);
            } else if(campaign.getTitle().equalsIgnoreCase("category")){
                categoryList.add(campaign);
            } else {
                campaignList.add(campaign);
            }
        }

        Collections.sort(baseList, new Comparator<Campaign>() {
            @Override
            public int compare(Campaign c1, Campaign c2) {
                return (int) (c2.getRewardRate() - c1.getRewardRate());
            }
        });

        double reward1 = 0.0;
        boolean fulfilled = false;
        String remarks = "";
        for(Campaign campaign : baseList){
            if(fulfilled){
                break;
            }

            if(campaign.getCustomCategoryName() != null){
                List<CustomCategory> customCategories = customCategoryService.getCustomCategory(campaign.getCustomCategoryName(), spendBean.getMerchant());
                if(customCategories.isEmpty()){
                    continue;
                }
                CustomCategory customCategory = customCategories.get(0);
                for(Mcc obj : customCategory.getMccList()){
                    if(spendBean.getMcc() == obj.getMcc()){
                        reward1 = campaignService.baseReward(campaign, spendBean);
                        remarks += BASE + campaign.getRewardRate();
                        fulfilled = true;
                    }
                }
            } else{
                if(campaign.isForeign() && !spendBean.getCurrency().equalsIgnoreCase("sgd") || !mccExclusionService.isExcluded(spendBean) ){
                    reward1 = campaignService.baseReward(campaign, spendBean);
                    remarks += BASE + campaign.getRewardRate();
                }
            }
        }

        if(!fulfilled){
            remarks += NOREWARD;
        }
        RewardBean rewardBean1 = createReward(spendBean, reward1, remarks);
        list.add(rewardBean1);

        reward1 = 0.0;
        fulfilled = false;
        remarks = "";
        for(Campaign campaign : categoryList){
            if(fulfilled){
                break;
            }
            if(campaign.getCustomCategoryName() != null){
                List<CustomCategory> customCategories = customCategoryService.getCustomCategory(campaign.getCustomCategoryName(), spendBean.getMerchant());
                if(customCategories.isEmpty()){
                    continue;
                }
                CustomCategory customCategory = customCategories.get(0);
                for(Mcc obj : customCategory.getMccList()){
                    if(spendBean.getMcc() == obj.getMcc()){
                        reward1 = campaignService.baseReward(campaign, spendBean);
                        remarks += BASE + campaign.getRewardRate();
                        fulfilled = true;
                    }
                }
            }
        }


        return list;
    }

    private RewardBean createReward(SpendBean spendBean, double reward, String remarks){
        return RewardBean.builder().tenant("SCIS").rewardAmount(reward).amount(spendBean.getAmount())
                .transaction_date(spendBean.getTransaction_date()).transaction_id(spendBean.getTransaction_id())
                .currency(spendBean.getCurrency()).merchant(spendBean.getMerchant()).card_id(spendBean.getCard_id())
                .mcc(spendBean.getMcc()).remarks(remarks).build();
    }

}
