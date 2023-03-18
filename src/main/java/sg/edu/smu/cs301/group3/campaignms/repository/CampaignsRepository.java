package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;

import java.util.List;

public interface CampaignsRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> getCampaignsByCardProgramId(int id);
    Campaign getCampaignByCampaignId(Long id);
    Campaign deleteByCampaignId(Long id);
    List<Campaign> findAll();
}
