package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Profesor;
import org.plantilla.sb.entities.Curso;

import java.sql.SQLException;
import java.util.List;

public interface ProfesorDAO {

    // CRUD
    List<Profesor> listAllProfesores() throws SQLException;

    void insertProfesor(Profesor profesor) throws SQLException;

    void updateProfesor(Profesor profesor) throws SQLException;

    void deleteProfesor(Long id) throws SQLException;

    Profesor getProfesorById(Long id) throws SQLException;

    // Relación N:M con Curso
    List<Curso> getCursosByProfesorId(Long profesorId) throws SQLException;

    void addCursoToProfesor(Long profesorId, Long cursoId) throws SQLException;

    void removeCursoFromProfesor(Long profesorId, Long cursoId) throws SQLException;
}
