package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignsRepository campaignsRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public List<Campaign> getAllCampaign() {
        return campaignsRepository.findAll();
    }

    public List<Campaign> getCampaignByCardId(int id) {
        return campaignsRepository.getCampaignsByCardProgramId(id);
    }

    public Campaign addCampaign(CampaignBean campaignBean) throws Exception {
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        Campaign campaign = Campaign
                .builder()
                .title(campaignBean.getTitle())
                .startDate(new Timestamp(startDate.getTime()))
                .endDate(new Timestamp(endDate.getTime()))
                .mcc(campaignBean.getMcc())
                .minDollarSpent(campaignBean.getMinDollarSpent())
                .rewardRate(campaignBean.getPointsPerDollar())
                .cardProgramId(campaignBean.getCardProgramId())
                .build();
        campaign.setActive(withinCampaignPeriod(campaign));
        return campaignsRepository.save(campaign);
    }

    public Campaign editCampaign(CampaignBean campaignBean, int campaignId) throws Exception {
        Campaign retrievedCampaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        if (retrievedCampaign == null) {
            throw new Exception("Campaign not found");
        }
        retrievedCampaign.setTitle(campaignBean.getTitle());
        retrievedCampaign.setStartDate(new Timestamp(startDate.getTime()));
        retrievedCampaign.setEndDate(new Timestamp(endDate.getTime()));
        retrievedCampaign.setMcc(campaignBean.getMcc());
        retrievedCampaign.setMinDollarSpent(campaignBean.getMinDollarSpent());
        retrievedCampaign.setRewardRate(campaignBean.getPointsPerDollar());
        retrievedCampaign.setActive(withinCampaignPeriod(retrievedCampaign));
        return campaignsRepository.save(retrievedCampaign);
    }

    public Campaign deleteCampaign(int campaignId) {
        return campaignsRepository.deleteByCampaignId(campaignId);
    }

    private boolean withinCampaignPeriod(Campaign campaign) {
        return campaign.getStartDate().before(new Date(System.currentTimeMillis())) && campaign.getEndDate().after(new Date(System.currentTimeMillis()));
    }
}
