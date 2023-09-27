package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    private CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepo.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepo.findAll();
    }

    public Customer getCustomer(Long id){
        return customerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
    }

    public Customer getPetOwner(Long id){
        return  customerRepo.findByPetsId(id).orElseThrow(() -> new EntityNotFoundException("Pet with id " + id + "is not associated with any customer"));
    }
}
