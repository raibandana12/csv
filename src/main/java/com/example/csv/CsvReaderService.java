package com.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public void uploadCsvFile(MultipartFile fileName) {

        try {
            customerRepository.saveAll(readCsvFile(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAllCsv() {
        customerRepository.deleteAll();
    }

    public List<Customer> getByCustomerRef(String customerRef) {

        return customerRepository.findByCustomerRef(customerRef);
    }

    public List<Customer> readCsvFile(MultipartFile fileName) throws FileNotFoundException {
        List<Customer> customerList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileName.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Customer customer = new Customer(
                        csvRecord.get("customerRef"),
                        csvRecord.get("customerName"),
                        csvRecord.get("addressline1"),
                        csvRecord.get("addressline2"),
                        csvRecord.get("town"),
                        csvRecord.get("county"),
                        csvRecord.get("country"),
                        csvRecord.get("postcode"));

                customerList.add(customer);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return customerList;
    }
}
