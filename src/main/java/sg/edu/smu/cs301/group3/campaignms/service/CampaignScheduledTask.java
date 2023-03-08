package sg.edu.smu.cs301.group3.campaignms.service;

import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.repository.CampaignsRepository;

public class CampaignScheduledTask implements Runnable{
    private final int campaignId;
    private final CampaignsRepository campaignsRepository;

    public CampaignScheduledTask(int campaignId, CampaignsRepository campaignsRepository) {
        this.campaignId = campaignId;
        this.campaignsRepository = campaignsRepository;
    }
    @Override
    public void run() {
        Campaign campaign = campaignsRepository.getCampaignByCampaignId(campaignId);
        if (campaign == null) {
            return;
        }
        campaign.setActive(!campaign.isActive());
        campaignsRepository.save(campaign);
    }
}
