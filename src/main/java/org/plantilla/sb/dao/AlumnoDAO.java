package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Alumno;
import org.plantilla.sb.entities.Curso;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDAO {

    // CRUD
    List<Alumno> listAllAlumnos() throws SQLException;

    void insertAlumno(Alumno alumno) throws SQLException;

    void updateAlumno(Alumno alumno) throws SQLException;

    void deleteAlumno(Long id) throws SQLException;

    Alumno getAlumnoById(Long id) throws SQLException;


    // Gestión de relación N:M (Alumno - Curso)
    List<Curso> getCursosByAlumnoId(Long alumnoId) throws SQLException;

    void addCursoToAlumno(Long alumnoId, Long cursoId) throws SQLException;

    void removeCursoFromAlumno(Long alumnoId, Long cursoId) throws SQLException;

    List<Curso> listAllCursos() throws SQLException;

    /*boolean save(Alumno alumno);
    boolean update(Alumno alumno);
    boolean delete(Long id);*/
}
