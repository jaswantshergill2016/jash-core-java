package io.reactivestax.service;

//import com.reactivestax.domain.Vendors;
import io.reactivestax.domain.Vendors;
import io.reactivestax.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public void createVendor(Vendors vendors) {

        vendorRepository.save(vendors);
    }

    public Vendors getVendor(String vendorId) {
        Optional<Vendors> vendors =  vendorRepository.findById(Integer.parseInt(vendorId));
        return vendors.get();
    }

    public void updateVendor(Vendors vendor, String vendorId) {
        Optional<Vendors> vendorRetrieved =  vendorRepository.findById(Integer.parseInt(vendorId));

        if(vendorRetrieved.isPresent()){
            vendor.setVendorId(vendorRetrieved.get().getVendorId());
            vendorRetrieved.get().setVendorName(vendor.getVendorName());
            vendorRetrieved.get().setVendorCode(vendor.getVendorCode());
            vendorRepository.save(vendorRetrieved.get());
        }
    }

    public void deleteVendor(String vendorId) {
        vendorRepository.deleteById(Integer.parseInt(vendorId));
    }
}
