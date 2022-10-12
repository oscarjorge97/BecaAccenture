package com.oyataco.demo.controllers;

import com.oyataco.demo.models.UsuarioModel;
import com.oyataco.demo.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

////EJECUTAR EN TERMINAL CON : .\mvnw.cmd spring-boot:run


@RestController//esta etiqueta le dira a Spring que esta es su funcion
@RequestMapping("/usuario")//nos dira en que direccion del servidor se va a activar los metodos de esta clase
public class UsuarioController {
    @Autowired//para que se instancie automaticamente
    UsuarioServices usuarioService;


    @GetMapping()//indicara que cuando llegue una peticion de tipo get, ejecute este flujo
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return usuarioService.obtenerUsuario();

    }


    @PostMapping() //
    public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario){//en el parametro recibe una etiqueta que significa que todos los clientes podran enviar info dentro del cuerpo de la solicitud http
        return this.usuarioService.guardarUsuario(usuario);//regresara el usuario actualizado
    }


    @GetMapping( path = "/{id}")//indicaremos que sera por ejemplo: http://.../1
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id")Long id){//Esta etiqueta indicará que nos referimos al id
        return this.usuarioService.obtenerPorId(id);
    }


    @GetMapping("/query")//esto nos haria un nuevo servicio,para buscar por prioridad pero pasando parametros como querypharams
    public ArrayList<UsuarioModel> obtenerUsuarioPorPrioridad (@RequestParam("prioridad") Integer prioridad){
        return this.usuarioService.obtenerPorPrioridad(prioridad);
    }


    @DeleteMapping(path = "/{id}")
    public String eliminarUsuarioporId(@PathVariable("id") Long id){
        boolean existe=this.usuarioService.eliminarUsuario(id);
        if (existe){
            return "Se eliminó al usuario con id "+id;
        }else {
            return "No se pudo eliminar al usuario con id "+id;
        }
    }
}
