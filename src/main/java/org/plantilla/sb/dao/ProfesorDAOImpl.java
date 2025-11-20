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

// JDBC PURO: Sin JPA ni ORM. Toda la persistencia se gestiona manualmente con SQL.
@Repository
public class ProfesorDAOImpl implements ProfesorDAO {

    // PATRÓN REUTILIZABLE: DataSource inyectado para obtener conexiones JDBC
    // Usa find and replace: "ProfesorDAOImpl" -> "NuevoDAOImpl" para duplicar este patrón
    @Autowired
    private DataSource dataSource;

    // JDBC PURO: Try-with-resources cierra automáticamente Connection, PreparedStatement y ResultSet
    // Patrón genérico: SELECT, mapear ResultSet a entidades, cargar relaciones N:M
    @Override
    public List<Profesor> list() throws SQLException {
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
                List<Curso> cursos = getRelacionados(profesor.getId());
                profesor.setCursos(new HashSet<>(cursos));
                profesores.add(profesor);
            }
        }

        return profesores;
    }

    // JDBC PURO - INSERT: PreparedStatement con Statement.RETURN_GENERATED_KEYS
    // CRÍTICO: Recupera el ID autogenerado para que la entidad esté disponible inmediatamente
    // (evita consultas SQL adicionales después de insert)
    @Override
    public void insert(Profesor profesor) throws SQLException {
        String sql = "INSERT INTO profesor (nombre, apellido) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getApellido());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    profesor.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Profesor profesor) throws SQLException {
        String sql = "UPDATE profesor SET nombre = ?, apellido = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getApellido());
            stmt.setLong(3, profesor.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM profesor WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Profesor getById(Long id) throws SQLException {
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

    // RELACIÓN N:M - getRelacionados: carga entidades relacionadas mediante tabla de unión
    // En este caso: Profesor <- [curso_profesor] -> Curso
    @Override
    public List<Curso> getRelacionados(Long profesorId) throws SQLException {
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

    // RELACIÓN N:M - addRelacion: crea vinculación en tabla de unión
    @Override
    public void addRelacion(Long profesorId, Long cursoId) throws SQLException {
        String sql = "INSERT INTO curso_profesor (curso_id, profesor_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }

    // RELACIÓN N:M - removeRelacion: elimina vinculación de tabla de unión
    @Override
    public void removeRelacion(Long profesorId, Long cursoId) throws SQLException {
        String sql = "DELETE FROM curso_profesor WHERE curso_id = ? AND profesor_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);
            stmt.setLong(2, profesorId);

            stmt.executeUpdate();
        }
    }
}
