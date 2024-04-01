package org.nurma.hackathontemplate.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class GenerateKeys {
    public static void main(final String[] args) {
        log.info(generateKey());
        log.info(generateKey());
    }

    public static String generateKey() {
        return Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
    }
}
