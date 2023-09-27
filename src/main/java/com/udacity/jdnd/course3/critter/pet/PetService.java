package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.EntityNotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    private PetRepo petRepo;
    private CustomerRepo customerRepo;
    public PetService(PetRepo petRepo, CustomerRepo customerRepo){
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    public Pet savePet(Pet pet){
        Pet savedPet = petRepo.save(pet);
        Customer customer = savedPet.getCustomer();
        List<Pet> customerPets = customer.getPets();
        if(customerPets == null){
            customerPets = new ArrayList<>();
        }
        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepo.save(customer);
        return savedPet;
    }

    public List<Pet> findAllPets(){
        return petRepo.findAll();
    }

    public Pet getPet(Long id){
        return  petRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Pet.class));
    }

    public List<Pet> getPetsByOwner(Long ownerId){
        return petRepo.findByCustomerId(ownerId);
    }
}
