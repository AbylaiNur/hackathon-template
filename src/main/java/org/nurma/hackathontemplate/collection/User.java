package org.nurma.hackathontemplate.collection;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseCollection {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;

    @Field("email")
    private String email;

    @Field("password")
    private String password;
}
