package mx.edu.utex.crudvue.controller;

import mx.edu.utex.crudvue.controller.dto.LibroDTO;
import mx.edu.utex.crudvue.model.Libro;
import mx.edu.utex.crudvue.service.LibroService;
import mx.edu.utex.crudvue.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api-crudvueback/libro")
@CrossOrigin({"*"})
public class LibroController{

    @Autowired
    private LibroService service;

    @GetMapping("/")
    public ResponseEntity<Response<List<Libro>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Response<Libro>> insert(@RequestBody LibroDTO libro){
        return new ResponseEntity<>(
                this.service.create(libro.getLibro()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Response<Libro>> update(@RequestBody LibroDTO libro){
        return new ResponseEntity<>(
                this.service.update(libro.getLibro()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Libro>> delete(@PathVariable long id){
        return new ResponseEntity<>(
                this.service.delete(id),
                HttpStatus.OK
        );
    }


}
