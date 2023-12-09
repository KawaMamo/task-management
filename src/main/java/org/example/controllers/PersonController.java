package org.example.controllers;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.example.client.Client;
import org.example.contract.requests.CreatePersonRequest;
import org.example.contract.requests.RegisterRequest;
import org.example.contract.responses.UserResponse;
import org.example.domain.Person;
import org.example.security.TokenService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;


@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private final PersonService personService;
    private final TokenService tokenService;
    private final Gson gson;
    @Autowired
    private EurekaClient discoveryClient;
    @Autowired
    Environment env;
    public PersonController(PersonService personService, TokenService tokenService, Gson gson) {
        this.personService = personService;
        this.tokenService = tokenService;
        this.gson = gson;
    }

    @PostMapping
    Person createPerson(@RequestBody CreatePersonRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {

        final InstanceInfo identity = discoveryClient.getApplications().getRegisteredApplications().stream().
                filter((app) -> app.getName().equals("identity-provider".toUpperCase())).
                findFirst().orElseThrow().getInstancesAsIsFromEureka().stream().findAny().orElseThrow();

        Client client = Client.getInstance(identity.getHomePageUrl());
        Client.setAuthorization(jwtToken);
        final RegisterRequest registerRequest = new RegisterRequest(request.getEmail(), request.getPassword(), request.getRole());
        final String json = gson.toJson(registerRequest);
        final HttpResponse<String> response = client.post("api/v1/register", json);
        final UserResponse userResponse = gson.fromJson(response.body(), UserResponse.class);
        return personService.createPerson(request, userResponse.getId());
    }
}
