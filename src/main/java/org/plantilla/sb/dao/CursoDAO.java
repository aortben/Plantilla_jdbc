package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Curso;
import org.plantilla.sb.entities.Profesor;

import java.sql.SQLException;
import java.util.List;

public interface CursoDAO {

    List<Curso> list() throws SQLException;

    void insert(Curso curso) throws SQLException;

    void update(Curso curso) throws SQLException;

    void delete(Long id) throws SQLException;

    Curso getById(Long id) throws SQLException;

    List<Profesor> getRelacionados(Long cursoId) throws SQLException;

    void addRelacion(Long cursoId, Long profesorId) throws SQLException;

    void removeRelacion(Long cursoId, Long profesorId) throws SQLException;
}

