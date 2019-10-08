package io.reactivestax;

import io.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.ResourceTypes;
import io.reactivestax.domain.Vendors;
import io.reactivestax.repository.ExpenseTypeRepository;
import io.reactivestax.repository.ResourceTypeRepository;
import io.reactivestax.repository.VendorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ResourceTypeRepository resourceTypesRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Test
    public void findByResourceResourceName (){
        ResourceTypes resourceTypes= new ResourceTypes();
        resourceTypes.setResourceName("Jaswant Shergill 1");
        resourceTypes.setResourceNickName("Jas");
        resourceTypes.setCreatedDt(new Date());
        resourceTypes.setCreatedBy(16);
        entityManager.persist(resourceTypes);
        entityManager.flush();

        ResourceTypes testResourceTypes = resourceTypesRepository.findResourceTypesByResourceName(resourceTypes.getResourceName());
        assertThat(testResourceTypes.getResourceName()).isEqualTo(resourceTypes.getResourceName());
    }

    @Test
    public void findByVendorName(){
        Vendors vendor =new Vendors();
        vendor.setVendorName("vendor name 1");
        vendor.setVendorCode("vendor code");
        vendor.setCreatedBy(16);
        vendor.setCreatedDt(new Date());
        entityManager.persist(vendor);
        entityManager.flush();

        Vendors testVendor = vendorRepository.findVendorsByVendorName(vendor.getVendorName());
        assertThat(testVendor.getVendorName()).isEqualTo(vendor.getVendorName());

    }

    @Test
    public void findByExpenseType (){
        ExpenseTypes expenseTypes= new ExpenseTypes();
        expenseTypes.setCreatedBy(6);
        expenseTypes.setExpenseDesc("expense desc");
        expenseTypes.setCreatedDt(new Date());
        expenseTypes.setExpenseType("expense type 1");
        entityManager.persist(expenseTypes);
        entityManager.flush();

        ExpenseTypes testExpenseTypes = expenseTypeRepository.findExpenseTypesByExpenseType(expenseTypes.getExpenseType());
        assertThat(testExpenseTypes.getExpenseType()).isEqualTo(expenseTypes.getExpenseType());
    }

}
