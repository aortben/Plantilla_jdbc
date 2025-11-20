package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Curso;
import org.plantilla.sb.entities.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// JDBC PURO: Gestiona relaciones N:M (Profesor-Curso) y 1:N (Curso-RA)
@Repository
public class CursoDAOImpl implements CursoDAO {

    // PATRÓN REUTILIZABLE: Usa find and replace "CursoDAOImpl" para duplicar estructura a otras entidades
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Curso> list() throws SQLException {
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

    @Override
    public void insert(Curso curso) throws SQLException {
        String sql = "INSERT INTO curso (nombre) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curso.getNombre());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    curso.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Curso curso) throws SQLException {
        String sql = "UPDATE curso SET nombre = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombre());
            stmt.setLong(2, curso.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Curso getById(Long id) throws SQLException {
        String sql = "SELECT id, nombre FROM curso WHERE id = ?";

        Curso curso = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    curso = new Curso();
                    curso.setId(rs.getLong("id"));
                    curso.setNombre(rs.getString("nombre"));
                }
            }
        }

        return curso;
    }

    @Override
    public List<Profesor> getRelacionados(Long cursoId) throws SQLException {
        List<Profesor> profesores = new ArrayList<>();

        String sql = """
                SELECT p.id, p.nombre, p.apellido
                FROM profesor p
                INNER JOIN curso_profesor cp ON p.id = cp.profesor_id
                WHERE cp.curso_id = ?
            """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Profesor profesor = new Profesor();
                    profesor.setId(rs.getLong("id"));
                    profesor.setNombre(rs.getString("nombre"));
                    profesor.setApellido(rs.getString("apellido"));

                    profesores.add(profesor);
                }
            }
        }

        return profesores;
    }

    @Override
    public void addRelacion(Long cursoId, Long profesorId) throws SQLException {
        String sql = "INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeRelacion(Long cursoId, Long profesorId) throws SQLException {
        String sql = "DELETE FROM curso_profesor WHERE curso_id = ? AND profesor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }
}

