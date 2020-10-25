package org.example.user.request;

import lombok.*;
import org.example.user.instance.User;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UserRequest {

    private String login;
    private String password;
    private String name;
    private String surname;

    public static Function<UserRequest, User> createMapper() {
        return request -> new User(request.getLogin(), request.getPassword(), request.getName(), request.getSurname());
    }

    public static Consumer<UserRequest> updateMapper(User user) {
        return request -> {
            user.setLogin(request.getLogin());
            user.setPasswordHash(request.getPassword().getBytes());
            user.setName(request.getName());
            user.setSurname(request.getSurname());
        };
    }

}
