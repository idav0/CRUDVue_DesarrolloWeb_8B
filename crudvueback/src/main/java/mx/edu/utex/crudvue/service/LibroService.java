package mx.edu.utex.crudvue.service;


import mx.edu.utex.crudvue.model.Libro;
import mx.edu.utex.crudvue.model.LibroRepository;
import mx.edu.utex.crudvue.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Transactional
@Service
public class LibroService {

    @Autowired
    private LibroRepository repository;

    @Transactional(readOnly = true)
    public Response<List<Libro>> getAll () {
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Libro> create(Libro libro) {
        return new Response<>(
                this.repository.saveAndFlush(libro),
                false,
                200,
                "OK"
        );
    }


    @Transactional(rollbackFor = SQLException.class)
    public Response<Libro> update (Libro libro){
        return new Response<>(
                this.repository.saveAndFlush(libro),
                false,
                200,
                "OK"
        );
    }


    @Transactional(rollbackFor = SQLException.class)
    public Response<Libro> delete (long id){
        this.repository.deleteById(id);
        return new Response<Libro>(
            null,
            false,
            200,
            "OK"
        );
    }
}
