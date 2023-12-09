package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.*;

public class EndPoints {

    @Autowired
    private Environment environment;

    private List<String> getPublicEndPoints(){
        List<String> publicEndPoints = new ArrayList<>();
        publicEndPoints.add("/swagger-ui/index.html");
        return publicEndPoints;
    }

    public boolean isAuthenticatedEndPoint(HttpServletRequest request){
        for (String publicEndPoint : getPublicEndPoints()) {
            if(publicEndPoint.equals(request.getServletPath()))
                return false;
        }
        return true;
    }

    private Map<String, ArrayList<String>> getEndpointsForRole(){
        Map<String, ArrayList<String>> endPoints = new HashMap<>();
        ArrayList<String> roles = environment.getProperty("roles", ArrayList.class);
        if(roles != null) {
            for (String role : roles) {
                final ArrayList<String> property = environment.getProperty(role + ".endpoints", ArrayList.class);
                if (property != null) {
                    ArrayList<String> roleEndPoints = new ArrayList<>(property);
                    endPoints.put(role, roleEndPoints);
                }
            }
        }
        return endPoints;
    }

    public boolean isNotAllowed(ArrayList<LinkedHashMap<String, String>> roles, String endPoint){
        for (LinkedHashMap<String, String> role : roles) {
            final ArrayList<String> authority = getEndpointsForRole().get(role.get("authority"));
            if(authority != null){
                for (String s : authority) {
                    if(endPoint.startsWith(s))
                        return false;
                }
            }

            /*if(authority != null && authority.contains(endPoint)) {
                return true;
            }*/
        }
        return true;
    }
}
