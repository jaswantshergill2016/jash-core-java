package io.reactivestax.service;

//import com.reactivestax.domain.ExpenseTypes;
import io.reactivestax.domain.Invoices;
import io.reactivestax.domain.Payments;
import io.reactivestax.repository.InvoiceRepository;
import io.reactivestax.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceService {



    @Autowired
    private InvoiceRepository invoiceRepository;


    public void createInvoice(Invoices invoices) {

        invoiceRepository.save(invoices);
    }

    public Invoices getInvoice(String invoiceId) {
        Optional<Invoices> invoices =  invoiceRepository.findById(Integer.parseInt(invoiceId));
        //Optional<Invoices> invoices =  invoiceRepository.findById(invoiceId);
        return invoices.get();
    }

    public Invoices updateInvoice(Invoices invoices, String invoiceId) {

        Optional<Invoices> invoicesRetrieved =  invoiceRepository.findById(Integer.parseInt(invoiceId));
        //Optional<Invoices> invoicesRetrieved =  invoiceRepository.findById(invoiceId);


        if(invoicesRetrieved.isPresent()){

            invoices.setInvoiceId(Integer.parseInt(invoiceId));
            invoiceRepository.save(invoices);
        }

        return invoices;
    }

    public void deleteInvoice(String invoiceId) {
        invoiceRepository.deleteById(Integer.parseInt(invoiceId));
        //invoiceRepository.deleteById(invoiceId);
    }
}


