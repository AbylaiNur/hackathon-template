package org.nurma.hackathontemplate.util;

import lombok.experimental.UtilityClass;
import org.nurma.hackathontemplate.collection.User;
import org.nurma.hackathontemplate.dto.response.GetUserResponse;

@UtilityClass
public class EntityToDTOMapper {
    public static GetUserResponse mapUserToGetUserResponse(final User user) {
        GetUserResponse getUserResponse = new GetUserResponse();
        getUserResponse.setId(user.getId().toString());
        getUserResponse.setEmail(user.getEmail());
        return getUserResponse;
    }
}
