package com.vbenadmin.backend.commonrpc.models.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
