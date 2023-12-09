package org.example.config;

import com.google.gson.Gson;
import org.example.mappers.*;
import org.example.security.EndPoints;
import org.example.security.TokenService;
import org.example.validators.TaskValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeans {
    @Bean
    TaskMapper taskMapper(){
        return new TaskMapperImpl();
    }

    @Bean
    TokenService tokenService(){
        return new TokenService();
    }

    @Bean
    EndPoints endPoints(){
        return new EndPoints();
    }

    @Bean
    PersonMapper personMapper(){
        return new PersonMapperImpl();
    }

    @Bean
    TaskValidator taskValidator(){ return new TaskValidator(); }

    @Bean
    Gson gson(){ return new Gson(); }

    @Bean
    CommentMapper commentMapper(){ return new CommentMapperImpl(); }

}
