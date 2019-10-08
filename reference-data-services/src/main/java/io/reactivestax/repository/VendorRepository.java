package io.reactivestax.repository;

//import com.reactivestax.domain.Vendors;
import io.reactivestax.domain.Vendors;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendors,Integer> {

    public Vendors findVendorsByVendorName(String vendorName);


}
