package fr.polytech.interfaces.customer;

import fr.polytech.exceptions.BadCredentialsException;
import fr.polytech.exceptions.CustomerNotFoundException;
import fr.polytech.exceptions.MalformedCredentialsExceptions;
import fr.polytech.pojo.Customer;

import java.util.Set;

public interface CustomerExplorer {
    /**
     * For a given id, return the customer with the given id
     * @param id The id of the customer to find
     * @return The customer associated with the given id
     * @throws CustomerNotFoundException The customer wasn't found
     */
    Customer findCustomerById(int id) throws CustomerNotFoundException;

    /**
     * For a given id, return all the customers with the given name
     * @param name The name of the customer to find
     * @return The customers associated with the given name
     */
    Set<Customer> findCustomersByName(String name);

    /**
     * Check the given credentials
     * @param name The name of the customer
     * @param password The password of the customer
     * @throws BadCredentialsException The credentials are mismatched
     * @throws MalformedCredentialsExceptions The credentials are malformed
     */
    void checkCredentials(String name, String password) throws BadCredentialsException, MalformedCredentialsExceptions;
}
