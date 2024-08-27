package com.example.rakyatgamezomeapi.service;

import com.example.rakyatgamezomeapi.constant.ERole;
import com.example.rakyatgamezomeapi.model.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
