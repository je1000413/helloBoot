package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Person;
import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.PersonRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class PersonRedisController {

    @Autowired
    private PersonRedisService personRedisService;

//    @GetMapping()
//    public ResponseEntity<List<Person>> getPerson(@RequestParam(required = false) String id) {
//        return ResponseEntity.ok(personRedisService.getById(id));
//    }
//
//    @PostMapping()
//    public void addPerson(@RequestBody Person person) {
//        personRedisService.addPerson(person);
//    }
}
