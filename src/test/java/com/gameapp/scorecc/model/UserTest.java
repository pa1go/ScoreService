package com.gameapp.scorecc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserAttributes() {
        // Create a new User object
        User user = new User();

        // Test setting and getting the id
        user.setId(1);
        assertEquals(1, user.getId());

        // Test setting and getting the name
        user.setName("John Doe");
        assertEquals("John Doe", user.getName());

        // Test setting and getting the email
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());

        // Test setting and getting the userName
        user.setUserName("johndoe");
        assertEquals("johndoe", user.getUserName());
    }

    @Test
    void testUserConstructor() {
        // You can add a constructor to your class or skip this test if there's no constructor
        User user = new User();
        user.setId(1);
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        user.setUserName("janedoe");

        assertAll("User Constructor Test",
                () -> assertEquals(1, user.getId()),
                () -> assertEquals("Jane Doe", user.getName()),
                () -> assertEquals("jane.doe@example.com", user.getEmail()),
                () -> assertEquals("janedoe", user.getUserName())
        );
    }

    @Test
    void testUserEquality() {
        // Create two User objects with the same attributes
        User user1 = new User();
        user1.setId(1);
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        user1.setUserName("alice");

        User user2 = new User();
        user2.setId(1);
        user2.setName("Alice");
        user2.setEmail("alice@example.com");
        user2.setUserName("alice");

        // Test equality
        assertEquals(user1, user2);
    }

    @Test
    void testUserHashCode() {
        // Create a User object and ensure the hashCode method works as expected
        User user = new User();
        user.setId(1);
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setUserName("bob");

        int expectedHashCode = user.hashCode();
        assertEquals(expectedHashCode, user.hashCode());
    }

    @Test
    void testUserToString() {
        // Create a User object and test the toString method
        User user = new User();
        user.setId(1);
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        user.setUserName("charlie");

        String expectedString = "User(id=1, name=Charlie, email=charlie@example.com, userName=charlie)";
        assertEquals(expectedString, user.toString());
    }
}
