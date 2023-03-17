package com.example.Trustifyrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartContractReaderController {

   @GetMapping("/GetNumber")
    public SmartContractReader SmartContractReader() throws Exception {
        return new SmartContractReader();
    }
}
