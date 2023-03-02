package fr.polytech.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String password;
    private String email;
    @OneToOne
    private FidelityAccount fidelityAccount;

    //@OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "customer")
    //private List<Payment> history;

    public Customer(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.fidelityAccount = new FidelityAccount(id);
        //this.history = new ArrayList<>();
    }

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public FidelityAccount getFidelityAccount() {
        return fidelityAccount;
    }

}
