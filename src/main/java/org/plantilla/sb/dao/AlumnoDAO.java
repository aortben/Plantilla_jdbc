package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Alumno;
import org.plantilla.sb.entities.Curso;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDAO {

    List<Alumno> list() throws SQLException;

    void insert(Alumno alumno) throws SQLException;

    void update(Alumno alumno) throws SQLException;

    void delete(Long id) throws SQLException;

    Alumno getById(Long id) throws SQLException;

    List<Curso> getRelacionados(Long alumnoId) throws SQLException;

    void addRelacion(Long alumnoId, Long cursoId) throws SQLException;

    void removeRelacion(Long alumnoId, Long cursoId) throws SQLException;

    List<Curso> listCursos() throws SQLException;
}
