package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.beans.SpendBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.CardTypeRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CampaignService {
    private final CampaignsRepository campaignsRepository;
    private final CardTypeRepository cardTypeRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public List<Campaign> getAllCampaign() {
        return campaignsRepository.findAll();
    }

    public List<Campaign> getCampaignByCardId(Long id) {

        CardType cardType = cardTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id + " not found"));

        return campaignsRepository.getCampaignsByCardType(cardType);
    }

    public Campaign getCampaignByCampaignId(Long id) {
        return campaignsRepository.getCampaignByCampaignId(id);
    }

    public Campaign addCampaign(CampaignBean campaignBean) throws Exception {

        CardType cardType = cardTypeRepository.findById(campaignBean.getCardProgramId())
                .orElseThrow(() -> new RuntimeException(campaignBean.getCardProgramId() + " not found"));

        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        Campaign campaign = Campaign
                .builder()
                .title(campaignBean.getTitle())
                .startDate(new Timestamp(startDate.getTime()))
                .endDate(new Timestamp(endDate.getTime()))
                .merchant(campaignBean.getMcc())
                .minDollarSpent(campaignBean.getMinDollarSpent())
                .rewardRate(campaignBean.getPointsPerDollar())
                .cardType(cardType)
                .build();
        campaign.setActive(withinCampaignPeriod(campaign));
        return campaignsRepository.save(campaign);
    }

    public Campaign editCampaign(CampaignBean campaignBean, Long campaignId) throws Exception {
        Campaign retrievedCampaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        if (retrievedCampaign == null) {
            throw new Exception("Campaign not found");
        }
        retrievedCampaign.setTitle(campaignBean.getTitle());
        retrievedCampaign.setStartDate(new Timestamp(startDate.getTime()));
        retrievedCampaign.setEndDate(new Timestamp(endDate.getTime()));
        retrievedCampaign.setMerchant(campaignBean.getMcc());
        retrievedCampaign.setMinDollarSpent(campaignBean.getMinDollarSpent());
        retrievedCampaign.setRewardRate(campaignBean.getPointsPerDollar());
        retrievedCampaign.setActive(withinCampaignPeriod(retrievedCampaign));
        return campaignsRepository.save(retrievedCampaign);
    }

    public Campaign deleteCampaign(Long campaignId) {
        return campaignsRepository.deleteByCampaignId(campaignId);
    }

    private boolean withinCampaignPeriod(Campaign campaign) {
        return campaign.getStartDate().before(new Date(System.currentTimeMillis())) && campaign.getEndDate().after(new Date(System.currentTimeMillis()));
    }

    public boolean isValid(Campaign campaign, Timestamp current){
        Timestamp startDate = campaign.getStartDate();
        Timestamp endDate = campaign.getEndDate();
        return startDate.before(current) && endDate.after(current);
    }

    public double baseReward(Campaign campaign, SpendBean spendBean){
        return campaign.getRewardRate() * spendBean.getAmount();
    }


}
