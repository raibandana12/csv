package com.example.csv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/csvfile")
public class CsvFileController {

    @Autowired
    CsvReaderService service;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            service.uploadCsvFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }

    @GetMapping("/customer/{customerRef}")
    public List<Customer> getRecordByCustomerRef(@PathVariable String customerRef) {
        return service.getByCustomerRef(customerRef);
    }

    @GetMapping("/allCustomer")
    public List<Customer> getAllCustomer() {
        return service.getAll();
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll() {
        service.deleteAllCsv();
        return ResponseEntity.status(HttpStatus.OK).body("file deleted");
    }

}
