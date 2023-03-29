package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.RewardBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.model.CustomCategory;
import sg.edu.smu.cs301.group3.campaignms.model.Mcc;
import sg.edu.smu.cs301.group3.campaignms.repository.CardTypeRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.MccExclusionRepository;

import java.util.*;

import static sg.edu.smu.cs301.group3.campaignms.constants.Remarks.*;

@RequiredArgsConstructor
@Service
public class SpendService {

    private final CardTypeRepository cardTypeRepository;

    private final MccExclusionService mccExclusionService;

    private final CampaignService campaignService;

    private final CustomCategoryService customCategoryService;

    private final MccExclusionRepository mccExclusionRepository;

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

        buildCampaignBuckets(campaigns, baseList, categoryList, campaignList);

        RewardBean rewardBean = null;

        for (int i = 0 ; i< baseList.size(); i++) {

            Campaign rule = baseList.get(i);

            if(rule.isForeign()) {
                rewardBean = processIsForeignReward(spendBean, rule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
                }
            }

            if(rule.getCustomCategoryName() != null || !rule.getCustomCategoryName().isEmpty()) {
                rewardBean = processCustomCategory(spendBean, rule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
                }
            }

            if(rule.getMinDollarSpent() > 0.0) {
                rewardBean = processMinimumSpendReward(spendBean, rule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
                }
            }

            if(i == baseList.size()) {
                list.add(processBaseReward(spendBean, rule));
            }
        }

        for (Campaign categoryRule: categoryList) {
            if(categoryRule.getCustomCategoryName() != null ||
                    !categoryRule.getCustomCategoryName().isEmpty() &&
                        categoryRule.isForeign()) {
                rewardBean = processCustomCategoryWithIsForeign(spendBean, categoryRule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
                }
            }

            if(categoryRule.getCustomCategoryName() != null ||
                    !categoryRule.getCustomCategoryName().isEmpty()) {
                rewardBean = processCustomCategoryWithIsForeign(spendBean, categoryRule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
                }
            }
        }

        for (Campaign campaignRule: campaignList) {
            if(campaignRule.getCustomCategoryName() != null ||
                    !campaignRule.getCustomCategoryName().isEmpty()) {
                rewardBean = processCustomCategoryWithIsForeign(spendBean, campaignRule);
                if(rewardBean!= null) {
                    list.add(rewardBean);
                    break;
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

    private RewardBean processBaseReward(SpendBean spendBean, Campaign campaign) {
        if(mccExclusionService.isExcluded(spendBean) ){
            return createReward(spendBean, 0.0, NOREWARD);
        } else {
            return createReward(spendBean, campaignService.computeReward(campaign, spendBean),
                    remarksFactory(campaign));
        }
    }

    private RewardBean processIsForeignReward(SpendBean spendBean, Campaign campaign) {
        if (!spendBean.getCurrency().equalsIgnoreCase("sgd")) {
            return createReward(spendBean, campaignService.computeReward(campaign, spendBean),
                    remarksFactory(campaign));
        }

        return null;
    }

    private RewardBean processCustomCategory(SpendBean spendBean, Campaign campaign) {

        List<CustomCategory> customCategories = customCategoryService.getCustomCategory(campaign.getCustomCategoryName(), spendBean.getMerchant());

        //Merchant is not part of the maintained customCategory
        if(customCategories.isEmpty()){
            return null;
        }

        //Given merchant is part of the custom category, only one merchant will be maintained for each custom category
        CustomCategory customCategory = customCategories.get(0);

        for(Mcc obj : customCategory.getMccList()){
            if(spendBean.getMcc() == obj.getMcc()){
                return createReward(spendBean, campaignService.computeReward(campaign, spendBean),
                        remarksFactory(campaign));
            }
        }

        //Merchant is part of the mainted customCategory but no matching mcc for the spend
        return null;
    }

    private RewardBean processCustomCategoryWithIsForeign(SpendBean spendBean, Campaign campaign) {

        RewardBean rewardBean = processIsForeignReward(spendBean, campaign);

        //isNotForeign
        if(rewardBean == null) {
            return null;
        }

        return processCustomCategory(spendBean, campaign);
    }

    private RewardBean processMinimumSpendReward(SpendBean spendBean, Campaign campaign) {

        if(spendBean.getAmount() >= campaign.getMinDollarSpent()) {
            return createReward(spendBean, campaignService.computeReward(campaign, spendBean),
                    remarksFactory(campaign));
        }

        return null;
    }

    private String remarksFactory(Campaign campaign) {
        if(campaign.getTitle().equalsIgnoreCase("base")) {
            return BASE + " " + campaign.getRewardRate();
        } else if (campaign.getTitle().equalsIgnoreCase("category")) {
            return CATEGORY + " " + campaign.getRewardRate();
        } else {
            return CAMPAIGN + " " + campaign.getRewardRate();
        }
    }

    public void buildCampaignBuckets(List<Campaign> rawCampaignList, List<Campaign> baseList, List<Campaign> categoryList, List<Campaign> campaignList) {
        for(Campaign campaign : rawCampaignList){
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
    }
}
