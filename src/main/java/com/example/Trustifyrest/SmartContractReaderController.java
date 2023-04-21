package com.example.Trustifyrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class SmartContractReaderController {

    @GetMapping("/reviews")
    public List<Review> SmartContractReader(
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "startRange") int startRange,
            @RequestParam(value = "endRange") int endRange) throws Exception {
        TrustifyContractReader trustify = new TrustifyContractReader(new Review_Request(address, startRange, endRange));
        try {
            return trustify.getReviews();
        } catch (ExecutionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: contract not found");
        } catch (ConnectException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: connection refused");
        }

    }


}
