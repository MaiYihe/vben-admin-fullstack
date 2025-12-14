package com.vbenadmin.backend.rbac.service;

import java.util.List;

public interface IRbacService {
    List<String> getAuthCodesByUserId(String userId);
}
