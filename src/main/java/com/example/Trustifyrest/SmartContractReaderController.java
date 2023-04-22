package com.example.Trustifyrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.tx.exceptions.ContractCallException;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class SmartContractReaderController {

    @GetMapping("/reviews")

    public List<Review> SmartContractReaderController(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "startRange") int startRange,
            @RequestParam(value = "endRange") int endRange) throws Exception {
        TrustifyContractReader trustify = new TrustifyContractReader(new Review_Request(address, startRange, endRange));
        List<Review> l = new ArrayList<>();
        try {
            l=trustify.getReviews();

        } catch (ExecutionException e) {
            if(e.getCause() instanceof ContractCallException)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: This company have not received any reviews yet");
            else
                if(e.getCause() instanceof  IndexOutOfBoundsException)
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: Contract is not deployed yet");
        }
        catch (ConnectException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: connection failed");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: unknown error");
        }
    return l;
    }


}
