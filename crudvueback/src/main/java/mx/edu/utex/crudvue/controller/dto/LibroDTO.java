package mx.edu.utex.crudvue.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.crudvue.model.Libro;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibroDTO {

    private long id;
    private String nombre;
    private String autor;
    private String genero;
    private String anio_publicacion;

    public Libro getLibro(){
        return new Libro(
                this.id,
                this.nombre,
                this.autor,
                this.genero,
                this.anio_publicacion
        );
    }

}
