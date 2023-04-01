package sg.edu.smu.cs301.group3.campaignms.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs301.group3.campaignms.model.Campaign;
import sg.edu.smu.cs301.group3.campaignms.model.CardType;
import sg.edu.smu.cs301.group3.campaignms.repository.CardTypeRepository;
import sg.edu.smu.cs301.group3.campaignms.repository.MccExclusionRepository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpendServiceTest {
    @Mock
    private CardTypeRepository cardTypeRepository;

    @Mock
    private MccExclusionService mccExclusionService;

    @Mock
    private CampaignService campaignService;

    @Mock
    private CustomCategoryService customCategoryService;

    @Mock
    private MccExclusionRepository mccExclusionRepository;


    @Test
    public void sortCampaign_givenCampaignList_shouldSortByDesc() throws ParseException {
        //arrange
        Campaign campaignMidRate = new Campaign(1L, "base", null,null,"Grab", 0.0, 2.0, false,
                new CardType(1L, "scis_premiummiles", "scis", null), true, null );

        Campaign campaignLowRate = new Campaign(2L, "base", null,null,"Grab", 0.0, 1.0, false,
                new CardType(1L, "scis_premiummiles", "scis", null), true, null );

        Campaign campaignHighRate = new Campaign(3L, "base", null,null,"Grab", 0.0, 3.0, false,
                new CardType(1L, "scis_premiummiles", "scis", null), true, null );


        List<Campaign> rawCampaignList = Arrays.asList(campaignLowRate, campaignMidRate, campaignHighRate);
        List<Campaign> baseList = new ArrayList<>();
        List<Campaign> categoryList = new ArrayList<>();
        List<Campaign> campaignList = new ArrayList<>();


        SpendService spendService = new SpendService(cardTypeRepository, mccExclusionService, campaignService, customCategoryService, mccExclusionRepository);

        //act
        spendService.buildCampaignBuckets(rawCampaignList, baseList, categoryList, campaignList);

        //assert
        assertThat(baseList.get(0).getCampaignId()).isEqualTo(3L);

    }
}
