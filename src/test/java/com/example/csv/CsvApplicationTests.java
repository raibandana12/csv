package com.example.csv;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CsvApplicationTests {

    @Mock
    CsvReaderService serviceMock;

    @Test
    void testReadCSVFile() {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.csv",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        doNothing().when(serviceMock).uploadCsvFile(file);
        serviceMock.uploadCsvFile(file);
        verify(serviceMock, times(1)).uploadCsvFile(file);
    }

    @Test
    void testGetRecords() {
        Customer customer1 = new Customer("cusref1", "name1", "addres1", "addres2", "town1", "kent", "england", "eew2323");
        Customer customer2 = new Customer("cusref2", "name2", "addres1", "addres2", "town1", "kent", "england", "eew2323");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        when(serviceMock.getByCustomerRef("cusref1")).thenReturn(customerList);
        List<Customer> actual = serviceMock.getByCustomerRef("cusref1");

        verify(serviceMock).getByCustomerRef("cusref1");
        assertThat(customerList, is(actual));

    }

}
