package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.beans.CampaignBean;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CampaignService {
    @Autowired
    private final CampaignsRepository campaignsRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public Campaign getCampaign(int id) {
        return campaignsRepository.getCampaignByCampaignId(id);
    }

    public Campaign addCampaign(CampaignBean campaignBean) throws Exception {
        Date startDate = formatter.parse(campaignBean.getStartDate());
        Date endDate = formatter.parse(campaignBean.getEndDate());
        Campaign campaign = Campaign
                .builder()
                .title(campaignBean.getTitle())
                .startDate(new Timestamp(startDate.getTime()).toString())
                .endDate(new Timestamp(endDate.getTime()).toString())
                .mcc(campaignBean.getMcc())
                .minDollarSpent(campaignBean.getMinDollarSpent())
                .pointsPerDollar(campaignBean.getPointsPerDollar())
                .cardProgramId(campaignBean.getCardProgramId())
                .cashbackAmount(campaignBean.getCashbackAmount())
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
        retrievedCampaign.setStartDate(new Timestamp(startDate.getTime()).toString());
        retrievedCampaign.setEndDate(new Timestamp(endDate.getTime()).toString());
        retrievedCampaign.setMcc(campaignBean.getMcc());
        retrievedCampaign.setMinDollarSpent(campaignBean.getMinDollarSpent());
        retrievedCampaign.setPointsPerDollar(campaignBean.getPointsPerDollar());
        retrievedCampaign.setCashbackAmount(campaignBean.getCashbackAmount());
        retrievedCampaign.setActive(withinCampaignPeriod(retrievedCampaign));
        return campaignsRepository.save(retrievedCampaign);
    }

    public Campaign deleteCampaign(int campaignId) {
        return campaignsRepository.deleteByCampaignId(campaignId);
    }

    private boolean withinCampaignPeriod(Campaign campaign) {
        try {
            Date startDate = formatter.parse(campaign.getStartDate());
            Date endDate = formatter.parse(campaign.getEndDate());
            return startDate.before(new Date(System.currentTimeMillis())) && endDate.after(new Date(System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
