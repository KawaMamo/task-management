package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class TokenService {


    @Autowired
    private Environment env;
    public String test(){
        return "TokenService.test";
    }

    public String getIdentityProviderAddress(){
        final String port = env.getProperty("identity.provider.port");
        return env.getProperty("identity.provider.host")+":"+port+"/";
    }

    public RSAPublicKey getPublicKey(){

        PublicKey publicKey = null;
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        final String publicKeystring = restTemplateBuilder.build()
                .getForObject(getIdentityProviderAddress()+"api/v1/pub/publicKey", String.class);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Base64.Decoder decoder = Base64.getDecoder();
            final byte[] decode = decoder.decode(publicKeystring);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
            publicKey = keyFactory.generatePublic(keySpec);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new RuntimeException(e);
        }
        return (RSAPublicKey) publicKey;
    }

    public String getJWTToken(String authHeader){
        if(Objects.nonNull(authHeader))
            return authHeader.substring("Bearer ".length());
        return "";
    }

    private Claims extractClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpiredToken(String jwtToken){
        return extractClaims(jwtToken).getExpiration().before(new Date());
    }

    public ArrayList<LinkedHashMap<String, String>> getRoles(String jwtToken){
        return (ArrayList<LinkedHashMap<String, String>>) extractClaims(jwtToken).get("Roles");
    }

    public void updateUserDetails(String jwtToken){
        UserDetails.UserId = Long.valueOf(extractClaims(jwtToken).get("UserId").toString());
    }

}
