package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Curso;
import org.plantilla.sb.entities.Profesor;

import java.sql.SQLException;
import java.util.List;

public interface CursoDAO {

    // CRUD
    List<Curso> listAllCursos() throws SQLException;

    void insertCurso(Curso curso) throws SQLException;

    void updateCurso(Curso curso) throws SQLException;

    void deleteCurso(Long id) throws SQLException;

    Curso getCursoById(Long id) throws SQLException;

    // Relación N:M con Profesor
    List<Profesor> getProfesoresByCursoId(Long cursoId) throws SQLException;

    void addProfesorToCurso(Long cursoId, Long profesorId) throws SQLException;

    void removeProfesorFromCurso(Long cursoId, Long profesorId) throws SQLException;
}

