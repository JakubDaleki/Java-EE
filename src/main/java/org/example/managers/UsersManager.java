package org.example.managers;

import org.example.user.instance.User;
import org.example.user.request.UserRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class UsersManager {
    private static ArrayList<User> users =
            new ArrayList<>(Arrays.asList(
                    new User("jdaleki", "haslo", "Jakub", "Daleki"),
                    new User("orion", "pass", "Marek", "Orion"),
                    new User("plut", "123", "Przemek", "Pluton"),
                    new User("blaz", "hash", "Blazej", "Krawiecki")));


    public static Optional<User> getUser(String uuid) throws IOException {
        var userToReturn = users.stream().filter(user -> user.getId().toString().equals(uuid)).findFirst();
        return userToReturn;
    }

    public static ArrayList<User> getUsers() throws IOException {
        return users;
    }

    public static String postUser(UserRequest requestBody) throws IOException {
        try {
            User user = UserRequest
                    .createMapper()
                    .apply(requestBody);

            users.add(user);
            return user.getId().toString();

        } catch (NullPointerException | IllegalArgumentException ex) {
            return "";
        }
    }

    public static boolean putUser(String uuid, UserRequest requestBody) throws IOException {

        var userToEdit = users.stream().filter(user -> user.getId().toString().equals(uuid)).findFirst();
        if (userToEdit.isPresent()) {
            UserRequest.updateMapper(userToEdit.get())
                    .accept(requestBody);
            return true;
        }
        return false;
    }

    public static boolean deleteUser(String uuid) throws IOException {
        var userToRemove = users.stream()
                .filter(user -> user.getId().toString().equals(uuid))
                .findFirst();
        if (userToRemove.isPresent()) {
            AvatarsManager.removeAvatar(uuid);
            users.remove(userToRemove.get());
            return true;
        }
        return false;
    }

    public static boolean checkIfExists(String uuid){
        var foundUser = users.stream().filter(user -> user.getId().toString().equals(uuid)).findFirst();
        return foundUser.isPresent();
    }
}
