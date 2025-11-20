package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Profesor;
import org.plantilla.sb.entities.Curso;

import java.sql.SQLException;
import java.util.List;

public interface ProfesorDAO {

    List<Profesor> list() throws SQLException;

    void insert(Profesor profesor) throws SQLException;

    void update(Profesor profesor) throws SQLException;

    void delete(Long id) throws SQLException;

    Profesor getById(Long id) throws SQLException;

    List<Curso> getRelacionados(Long profesorId) throws SQLException;

    void addRelacion(Long profesorId, Long cursoId) throws SQLException;

    void removeRelacion(Long profesorId, Long cursoId) throws SQLException;
}
