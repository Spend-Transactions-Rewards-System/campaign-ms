package sg.edu.smu.cs301.group3.campaignms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PulseController {

    @GetMapping("/")
    public ResponseEntity<String> checkPulse() {
        return ResponseEntity.ok("Campaign-Rewards ms at your service");
    }
}
