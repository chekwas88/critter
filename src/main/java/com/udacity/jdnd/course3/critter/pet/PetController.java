package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.Mapper;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final  PetService petService;
    private  final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService){
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = Mapper.convertPetDTOToPet(petDTO, customerService );
        return Mapper.convertPetToPetDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return Mapper.convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return Mapper.convertPetsToPetDTOs(petService.findAllPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return Mapper.convertPetsToPetDTOs(petService.getPetsByOwner(ownerId));
    }
}
