package com.example.Trustifyrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SmartContractReaderController {

    @GetMapping("/reviews")
    public List<Review> SmartContractReader(
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "startRange", defaultValue = "1") int startRange,
            @RequestParam(value = "endRange", defaultValue = "10") int endRange) throws Exception {
        TrustifyAPIREST trustify = new TrustifyAPIREST(address, startRange, endRange);

            return trustify.getReviews();
    }


}
