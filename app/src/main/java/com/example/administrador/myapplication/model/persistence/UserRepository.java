package com.example.administrador.myapplication.model.persistence;

import com.example.administrador.myapplication.model.entities.User;


public interface UserRepository {
    public abstract void save(User user);
}
