package org.solent.com504.factoryandfacade.impl.service;

import java.util.ArrayList;
import java.util.List;
import org.solent.com504.factoryandfacade.model.dao.AnimalDao;
import org.solent.com504.factoryandfacade.model.dao.AnimalTypeDao;
import org.solent.com504.factoryandfacade.model.dto.Animal;
import org.solent.com504.factoryandfacade.model.dto.AnimalList;
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
        return animalDao.retrieve(tempAnimal).get(0);
    }

    @Override
    public boolean removeAnimal(String name) {
        animalDao.delete(tempAnimal.getId());
            return true;
    }

    @Override
    public List<String> getSupportedAnimalTypes() {
        animalTypeDao.getSupportedAnimalTypes();
    }
}
