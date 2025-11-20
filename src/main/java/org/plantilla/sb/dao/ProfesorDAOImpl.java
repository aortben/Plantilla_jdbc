package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Profesor;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public class ProfesorDAOImpl implements ProfesorDAO {

    @Autowired
    private DataSource dataSource;

    // -------------------------------------------------------
    // LIST ALL
    // -------------------------------------------------------
    @Override
    public List<Profesor> listAllProfesores() throws SQLException {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido FROM profesor";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Profesor profesor = new Profesor();
                profesor.setId(rs.getLong("id"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setApellido(rs.getString("apellido"));
                List<Curso> cursos = getCursosByProfesorId(profesor.getId());
                profesor.setCursos(new HashSet<>(cursos));
                profesores.add(profesor);
            }
        }

        return profesores;
    }

    // -------------------------------------------------------
    // INSERT
    // -------------------------------------------------------
    @Override
    public void insertProfesor(Profesor profesor) throws SQLException {
        String sql = "INSERT INTO profesor (nombre, apellido) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getApellido());

            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------
    @Override
    public void updateProfesor(Profesor profesor) throws SQLException {
        String sql = "UPDATE profesor SET nombre = ?, apellido = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getApellido());
            stmt.setLong(3, profesor.getId());

            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // DELETE
    // -------------------------------------------------------
    @Override
    public void deleteProfesor(Long id) throws SQLException {
        String sql = "DELETE FROM profesor WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------
    @Override
    public Profesor getProfesorById(Long id) throws SQLException {
        Profesor profesor = null;
        String sql = "SELECT id, nombre, apellido FROM profesor WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    profesor = new Profesor();
                    profesor.setId(rs.getLong("id"));
                    profesor.setNombre(rs.getString("nombre"));
                    profesor.setApellido(rs.getString("apellido"));
                }
            }
        }

        return profesor;
    }

    // -------------------------------------------------------
    // GET CURSOS (N:M)
    // -------------------------------------------------------
    @Override
    public List<Curso> getCursosByProfesorId(Long profesorId) throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = """
                SELECT c.id, c.nombre
                FROM curso c
                INNER JOIN curso_profesor cp ON c.id = cp.curso_id
                WHERE cp.profesor_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, profesorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setId(rs.getLong("id"));
                    curso.setNombre(rs.getString("nombre"));
                    cursos.add(curso);
                }
            }
        }

        return cursos;
    }

    // -------------------------------------------------------
    // ADD CURSO TO PROFESOR (N:M)
    // -------------------------------------------------------
    @Override
    public void addCursoToProfesor(Long profesorId, Long cursoId) throws SQLException {
        String sql = "INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // REMOVE CURSO FROM PROFESOR (N:M)
    // -------------------------------------------------------
    @Override
    public void removeCursoFromProfesor(Long profesorId, Long cursoId) throws SQLException {
        String sql = "DELETE FROM curso_profesor WHERE curso_id = ? AND profesor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }

    // -------------------------------------------------------
    // LIST ALL CURSOS
    // -------------------------------------------------------
    @Override
    public List<Curso> listAllCursos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM curso";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getLong("id"));
                curso.setNombre(rs.getString("nombre"));
                cursos.add(curso);
            }
        }

        return cursos;
    }
}
