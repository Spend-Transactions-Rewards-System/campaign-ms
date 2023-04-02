package sg.edu.smu.cs301.group3.campaignms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs301.group3.campaignms.model.Customer;
import sg.edu.smu.cs301.group3.campaignms.repository.CustomerRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomerGivenEmail(String email) {

        List<Customer> customers = customerRepository.findByEmail(email);
        if(customers.size()==0) {
            throw new RuntimeException("No customer found for email: " + email);
        }

        return customers.get(0);
    }
}
