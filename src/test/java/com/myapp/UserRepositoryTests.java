package com.myapp;

import com.myapp.user.User;
import com.myapp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void addNew(){
        User user = new User();
        user.setEmail("jarjarRocks@gmail.com");
        user.setPassword("jar123");
        user.setFirstName("jarjar");
        user.setLastName("binks");

        User savedUser = repo.save(user);

        Assertions.assertNotNull(savedUser);
    }

    @Test
    public void listAll (){
        Iterable<User> users = repo.findAll();
        Assertions.assertNotNull(users);

        for (User user : users){
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setLastName("Walker");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertEquals(updatedUser.getLastName(), "Walker");
    }

    @Test
    public void testGet(){
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertEquals(optionalUser.isPresent(), true);
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertEquals(optionalUser.isPresent(), false);

    }
}
