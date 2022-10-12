package com.oyataco.demo.services;

import com.oyataco.demo.models.UsuarioModel;
import com.oyataco.demo.repositories.UsuarioRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

//EJECUTAR EN TERMINAL CON : .\mvnw.cmd spring-boot:run



//Para que se convierta en una clase tipo servicio
@Service
public class UsuarioServices {
    @Autowired //Esto va a hacer que no tengamos que instanciarla
    UsuarioRepositories usuarioRepository;



    public ArrayList<UsuarioModel> obtenerUsuario(){
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();//Obtendremos todos los registros
            //casteamos a un array de tipo usuario model para poder regresarlo en un JSON

    }



    //metodo para guardar usuario, registrar al usuario
    public UsuarioModel guardarUsuario(UsuarioModel usuario){//agregando el parametro usuario obtendremos el id del mismo
        return usuarioRepository.save(usuario);//devolveremos el id, el nombre y el email

    }

    //metodo para encontrar a traves del id del usuario
    public Optional<UsuarioModel> obtenerPorId (Long id){
        return usuarioRepository.findById(id);
    }

    //metodo para encontrar a traves de la prioridad del usuario
    public ArrayList<UsuarioModel> obtenerPorPrioridad(Integer prioridad){
        return usuarioRepository.encontrarporprioridad(prioridad);
    }

    //Eliminar usuario
    public boolean eliminarUsuario(Long id){
        try {
            usuarioRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;

        }
    }

}
