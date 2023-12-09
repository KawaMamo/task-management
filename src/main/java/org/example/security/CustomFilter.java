package org.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

import static org.apache.tomcat.websocket.Constants.UNAUTHORIZED;

@Component
public class CustomFilter implements Filter {

    private final TokenService tokenService;
    private final EndPoints endPoints;

    public CustomFilter(TokenService tokenService, EndPoints endPoints) {
        this.tokenService = tokenService;
        this.endPoints = endPoints;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger(CustomFilter.class);

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String authorization = request.getHeader("AUTHORIZATION");
        String jwtToken = tokenService.getJWTToken(authorization);

        JWTVerifier verifier = JWT.require(Algorithm.RSA256(tokenService.getPublicKey(), null)).build();
        logger.atWarn().log(request.getServletPath()+"");

        if(endPoints.isAuthenticatedEndPoint(request) && !request.getServletPath().startsWith("/api/v1/pub/")){
            DecodedJWT verify = null;
            try {
                verify = verifier.verify(jwtToken);
            }catch (Exception e){
                ((HttpServletResponse) servletResponse).addHeader("TOKEN-STATUS", "Invalid");
                ((HttpServletResponse) servletResponse).sendError(UNAUTHORIZED, "Unauthorized");
            }

            if(Objects.nonNull(verify)){
                if(tokenService.isExpiredToken(jwtToken)){
                    ((HttpServletResponse) servletResponse).addHeader("tokenStatus", "Expired");
                    ((HttpServletResponse) servletResponse).sendError(UNAUTHORIZED, "Unauthorized");
                }

                String path = request.getServletPath();
                final String[] split = path.split("/");
                int anInt = 0;
                try {
                    anInt = Integer.parseInt(split[split.length - 1]);
                    if( anInt > 0){
                        path = path.substring(0, (path.length() - split[split.length-1].length()));
                    }
                }catch (NumberFormatException ignored){

                }

                final ArrayList<LinkedHashMap<String, String>> roles = tokenService.getRoles(jwtToken);
                if(endPoints.isNotAllowed(roles, path)){
                    logger.atWarn().log(path+" is not allowed for "+roles+request.getHeaders("AUTHORIZATION"));
                    ((HttpServletResponse) servletResponse).addHeader("tokenStatus", "Unauthorized User");
                    ((HttpServletResponse) servletResponse).sendError(UNAUTHORIZED, "Unauthorized");
                    throw new RuntimeException("Unauthorized");
                }
                tokenService.updateUserDetails(jwtToken);
                logger.atWarn().log(path+" headers "+request.getHeaders("AUTHORIZATION").toString());
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
