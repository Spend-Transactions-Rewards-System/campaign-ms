package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs301.group3.campaignms.model.CustomCategory;

import java.util.List;

@Repository
public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {

    List<CustomCategory> findByNameAndMerchant(String name, String merchant);
}