package org.team.bookshop.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.team.bookshop.domain.user.entity.User;

@ActiveProfiles("local")
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void testSaveUser() {
        User user = new User();
        // Set properties for the user
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");

        User savedUser = userService.saveUser(user);
        User foundUser = entityManager.find(User.class, savedUser.getId());

        assertNotNull(savedUser);
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setName("Jane Doe");
        entityManager.persist(user);
        entityManager.flush();

        User foundUser = userService.getUserById(user.getId());
        assertEquals("Jane Doe", foundUser.getName());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        List<User> users = userService.getAllUsers();
        assertTrue(users.size() >= 2);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        entityManager.persist(user);
        entityManager.flush();

        userService.deleteUser(user.getId());
        assertThrows(RuntimeException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setName("Initial Name");
        entityManager.persist(user);
        entityManager.flush();

        user.setName("Updated Name");
        User updatedUser = userService.updateUser(user.getId(), user);
        assertEquals("Updated Name", updatedUser.getName());
    }
}
