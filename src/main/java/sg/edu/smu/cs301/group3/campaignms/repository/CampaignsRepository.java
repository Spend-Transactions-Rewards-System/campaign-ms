package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.repository.CrudRepository;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;

import java.util.List;

public interface CampaignsRepository extends CrudRepository<Campaign, Long> {
    List<Campaign> getCampaignsByCardProgramId(int id);
    Campaign getCampaignByCampaignId(int id);
    Campaign deleteByCampaignId(int id);
    List<Campaign> findAll();
}
