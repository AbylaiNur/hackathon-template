package org.nurma.hackathontemplate.security;

import io.jsonwebtoken.Claims;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtils {

    public static JwtAuthentication generate(final Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setId(claims.get("id", String.class));
        jwtInfoToken.setEmail(claims.get("email", String.class));
        return jwtInfoToken;
    }

}
