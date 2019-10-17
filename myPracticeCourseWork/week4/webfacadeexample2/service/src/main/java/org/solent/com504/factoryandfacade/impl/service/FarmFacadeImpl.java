package org.solent.com504.factoryandfacade.impl.service;

import java.util.ArrayList;
import java.util.List;
import org.solent.com504.factoryandfacade.model.dao.AnimalDao;
import org.solent.com504.factoryandfacade.model.dao.AnimalTypeDao;
import org.solent.com504.factoryandfacade.model.dto.Animal;
import org.solent.com504.factoryandfacade.model.dto.AnimalType;
import org.solent.com504.factoryandfacade.model.service.FarmFacade;

public class FarmFacadeImpl implements FarmFacade {

    private AnimalDao animalDao = null;

    private AnimalTypeDao animalTypeDao = null;

   // setters for DAOs
    public void setAnimalDao(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    public void setAnimalTypeDao(AnimalTypeDao animalTypeDao) {
        this.animalTypeDao = animalTypeDao;
    }
    
    FarmFacadeImpl farmFacade = new FarmFacadeImpl();
    
    // Farm facade methods
    @Override
    public List<Animal> getAllAnimals() {
        return animalDao.retrieveAll();
    }

    @Override
    public Animal addAnimal(String animalType, String name) {
        Animal tempAnimal = animalDao.create(animalTypeDao.getAnimalType(animalType));
        tempAnimal.setName(name);
        return animalDao.updateOrSave(tempAnimal);
    }

    @Override
    public List<Animal> getAnimalsOfType(String animalType) {
        Animal tempAnimal = animalDao.create(animalTypeDao.getAnimalType(animalType));
        return animalDao.retrieve(tempAnimal);
    }

    @Override
    public Animal getAnimal(String name) {
        Animal tempAnimal = animalDao.create(null);
        tempAnimal.setName(name);
        List<Animal> tempAnimals = animalDao.retrieve(tempAnimal);
        if(tempAnimals.isEmpty()){
            return null;
        }
        Animal finalAnimal = tempAnimals.get(0);
        return animalDao.retrieve(finalAnimal.getId());
    }

    @Override
    public boolean removeAnimal(String name) {
        Animal animalTemplate = animalDao.create(null);
        animalTemplate.setName(name);
        List<Animal> tempAnimals = animalDao.retrieve(animalTemplate);
        if(tempAnimals.isEmpty()){
        return false;
        }
        for(Animal animal : tempAnimals){
            animalDao.delete(animal.getId());
        }
        return true;
    }

    @Override
    public List<String> getSupportedAnimalTypes() {
        List<String> supportedTypes = new ArrayList<>();
        
        for(AnimalType animalType : animalTypeDao.getSupportedAnimalTypes()){
            supportedTypes.add(animalType.getType());
        }
        return supportedTypes;
    }
}
