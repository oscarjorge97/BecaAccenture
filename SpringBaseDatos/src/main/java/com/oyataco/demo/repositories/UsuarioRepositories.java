package com.oyataco.demo.repositories;

import com.oyataco.demo.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

////EJECUTAR EN TERMINAL CON : .\mvnw.cmd spring-boot:run


@Repository
public interface UsuarioRepositories extends CrudRepository<UsuarioModel,Long> {
    public abstract ArrayList<UsuarioModel> encontrarporprioridad(Integer prioridad);
}
