package io.reactivestax;


import io.reactivestax.domain.Vendors;
import io.reactivestax.repository.VendorRepository;
import io.reactivestax.service.ExpenseTypeService;
import io.reactivestax.service.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class MockTests {

    @Autowired
    private VendorService vendorService;

    @MockBean
    private VendorRepository vendorsRepository;

    @Before
    public void setUp(){
        Vendors vendor1=new Vendors();
        vendor1.setVendorName("JaswantShergill");
        vendor1.setVendorCode("vendor code");
        vendor1.setCreatedBy(18);
        vendor1.setCreatedDt(new Date());

        Mockito.when(vendorsRepository.findVendorsByVendorName(vendor1.getVendorName()))
                .thenReturn(vendor1);

    }
    @Test
    public void  whenValidName_thenVendorShouldBeFound(){
        String vendorName ="JaswantShergill";
        Vendors found =null;
        //Vendors found =  vendorService.findVendorByName(vendorName);

        assertThat (found.getVendorName()).isEqualTo (vendorName);
    }

}
