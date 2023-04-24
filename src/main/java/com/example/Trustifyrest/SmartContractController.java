package com.example.Trustifyrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.web3j.tx.exceptions.ContractCallException;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class SmartContractController {
    private TrustifyContractReader reader;
    @GetMapping("/reviews")
    public List<Review> SmartContractController(
            @RequestParam(value = "address") String address,
            @RequestParam(value = "startRange") int startRange,
            @RequestParam(value = "endRange") int endRange) throws Exception {
         reader = new TrustifyContractReader(new Review_Request(address, startRange, endRange));
         return getReviews(reader);
    }
    public List<Review> getReviews(TrustifyContractReader reader) {
        List<Review> l;
        try {
            l = reader.getReviews();

        } catch (ExecutionException e) {
            if (e.getCause() instanceof ContractCallException)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: This company have not received any reviews yet");
            else
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: Contract is not deployed yet");
        } catch (ConnectException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: connection failed");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: unknown error");
        }
        return l;
    }
    @GetMapping("/*")
    public void ExceptionHandlerHttpRequestController() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: the requested resource was not found");
    }


}
