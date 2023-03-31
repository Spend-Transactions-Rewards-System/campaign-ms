package sg.edu.smu.cs301.group3.campaignms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;

import java.util.Optional;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Long> {
    Optional<CardType> findByName(String name);
}
