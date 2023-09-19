package com.mice.backoffice.c2v_mice_backoffice_api.service.sample.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Person;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.sample.PersonRedisRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.PersonRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonRedisServiceImpl implements PersonRedisService {
    private final PersonRedisRepository personRedisRepository;

    @Override
    public List<Person> getById(String id) {

        List<Person> personList = new ArrayList<>();

        if("".equals(id) || id == null){
            personList = (List<Person>) personRedisRepository.findAll();
        }
        else{
            Optional<Person> optional = personRedisRepository.findById(id);
            if(optional.isPresent()){
                Person person = optional.get();
                personList.add(person);
            }
        }
        return personList;
    }

    @Override
    public void addPerson(Person person) {
        personRedisRepository.save(person);
    }
}
