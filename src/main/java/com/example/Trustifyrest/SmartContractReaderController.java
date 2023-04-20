package com.example.Trustifyrest;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SmartContractReaderController {

    @GetMapping("/reviews")
    public List<Review> SmartContractReader(
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "startRange") int startRange,
            @RequestParam(value = "endRange") int endRange) throws Exception {
        TrustifyContractReader trustify = new TrustifyContractReader(new Review_Request(address, startRange, endRange));

            return trustify.getReviews();


    }


}
