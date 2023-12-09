package org.example.contract.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Role;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
