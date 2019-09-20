package io.reactivestax.service;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.Expenses;
import io.reactivestax.domain.Payments;
import io.reactivestax.repository.ExpensesRepository;
import io.reactivestax.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {



    @Autowired
    private PaymentRepository paymentRepository;


    public void createPayment(Payments payment) {

        paymentRepository.save(payment);
    }

    public Payments getPayment(String paymentId) {
        Optional<Payments> payments =  paymentRepository.findById(Integer.parseInt(paymentId));
        return payments.get();
    }

    public Payments updatePayment(Payments payments, String paymentId) {

        Optional<Payments> paymentsRetrieved =  paymentRepository.findById(Integer.parseInt(paymentId));


        if(paymentsRetrieved.isPresent()){

            payments.setPaymentId(Integer.parseInt(paymentId));
            paymentRepository.save(payments);
        }

        return payments;
    }

    public void deletePayment(String paymentId) {
        paymentRepository.deleteById(Integer.parseInt(paymentId));
    }
}


