package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.repository.CrudRepository;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;

public interface CampaignsRepository extends CrudRepository<Campaign, Long> {
    Campaign getCampaignByCampaignId(int id);
    Campaign deleteByCampaignId(int id);
}
