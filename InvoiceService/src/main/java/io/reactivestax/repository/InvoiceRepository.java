package io.reactivestax.repository;

import io.reactivestax.domain.Invoices;
import io.reactivestax.domain.Payments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoices,Integer> {


}
