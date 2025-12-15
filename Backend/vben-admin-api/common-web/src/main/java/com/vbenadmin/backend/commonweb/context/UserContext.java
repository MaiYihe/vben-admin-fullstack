package com.vbenadmin.backend.commonweb.context;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserContext {
    
    private String userId;
    private Set<String> authCodes;
}
