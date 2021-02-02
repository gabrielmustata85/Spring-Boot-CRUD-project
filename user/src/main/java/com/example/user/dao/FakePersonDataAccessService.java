package com.example.user.dao;

import com.example.user.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getFirstName(), person.getLastName(), person.getEmail(), person.getAddress()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();

    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
            if (personMaybe.equals(0)){
                return 0;
            }
        DB.remove(personMaybe.get());
            return 1;

    }

    @Override
    public int updatePersonBYID(UUID id, Person update) {
        return selectPersonById(id)
                .map(person ->{
                    int indexOfPersonToUpdate= DB.indexOf(person);
                    if (indexOfPersonToUpdate >= 0){
                        DB.set(indexOfPersonToUpdate, new Person(id, update.getFirstName(), update.getLastName(), update.getEmail(), update.getAddress()));
                        return 1;
                    }
                            return 0;

                })
                .orElse(0);
    }
}
