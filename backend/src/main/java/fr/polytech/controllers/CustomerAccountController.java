package fr.polytech.controllers;

import fr.polytech.exceptions.*;
import fr.polytech.exceptions.paiment.NegativeAmountException;
import fr.polytech.exceptions.paiment.PaymentException;
import fr.polytech.interfaces.customer.CustomerExplorer;
import fr.polytech.interfaces.customer.CustomerRegistration;
import fr.polytech.interfaces.fidelity.FidelityExplorer;
import fr.polytech.interfaces.payment.RefillFidelityCard;
import fr.polytech.entities.Customer;
import fr.polytech.entities.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = fr.polytech.controllers.CustomerAccountController.BASE_URI, produces = APPLICATION_JSON_VALUE)
// referencing the same BASE_URI as Customer care to extend it hierarchically
public class CustomerAccountController {
    public static final String BASE_URI = "/customers";
    public static final String CUSTOMER_URI = "/{customerId}/";
    private final RefillFidelityCard refillFidelityCard;
    private final CustomerRegistration customerRegistration;
    private final CustomerExplorer customerExplorer;
    private final FidelityExplorer fidelityExplorer;

    @Autowired
    public CustomerAccountController(RefillFidelityCard refillFidelityCard, FidelityExplorer fidelityExplorer, CustomerRegistration customerRegistration, CustomerExplorer customerExplorer) {
        this.refillFidelityCard = refillFidelityCard;
        this.customerRegistration = customerRegistration;
        this.customerExplorer = customerExplorer;
        this.fidelityExplorer = fidelityExplorer;
    }

    @PostMapping(path = CUSTOMER_URI + "/refill")
    public ResponseEntity<String> refillAccount(@PathVariable("customerId") Long customerId, @RequestBody PaymentDTO transaction) throws CustomerNotFoundException, NegativeAmountException, PaymentException, FidelityAccountNotFoundException {
        Date refillTime = refillFidelityCard.refill(fidelityExplorer.findFidelityAccountById(customerId), transaction);
        return ResponseEntity.ok().body("Transaction ok! At: " + refillTime.toString() + " . Transaction amount: " + transaction.getAmount());
    }

    @PostMapping(path = "/registration", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> register(String name, String mail, String password) throws MailAlreadyUsedException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(customerRegistration.register(name, mail, password));
        } catch (MailAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping(path = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> login(String mail, String password) throws MailAlreadyUsedException {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(customerExplorer.checkCredentials(mail, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
