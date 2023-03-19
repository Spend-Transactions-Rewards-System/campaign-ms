package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
