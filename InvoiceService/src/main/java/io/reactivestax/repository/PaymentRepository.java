package io.reactivestax.repository;

import io.reactivestax.domain.Payments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payments,Integer> {


}
