package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Aula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// JDBC PURO - RELACIÓN 1:1: Cada Aula pertenece a exactamente un Curso (UNIQUE KEY en BD)
@Repository
public class AulaDAOImpl implements AulaDAO {

    // PATRÓN REUTILIZABLE: Para nueva entidad con relación 1:1, cambiar nombre de clase y métodos
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Aula> list() throws SQLException {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT id, nombre, edificio, planta, curso_id FROM aula";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getLong("id"));
                aula.setNombre(rs.getString("nombre"));
                aula.setEdificio(rs.getString("edificio"));
                aula.setPlanta(rs.getInt("planta"));
                aula.setCursoId(rs.getLong("curso_id"));
                aulas.add(aula);
            }
        }

        return aulas;
    }

    @Override
    public void insert(Aula aula) throws SQLException {
        String sql = "INSERT INTO aula (nombre, edificio, planta, curso_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aula.getNombre());
            stmt.setString(2, aula.getEdificio());
            stmt.setInt(3, aula.getPlanta() != null ? aula.getPlanta() : 0);
            
            if (aula.getCursoId() != null) {
                stmt.setLong(4, aula.getCursoId());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aula.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Aula aula) throws SQLException {
        String sql = "UPDATE aula SET nombre = ?, edificio = ?, planta = ?, curso_id = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aula.getNombre());
            stmt.setString(2, aula.getEdificio());
            stmt.setInt(3, aula.getPlanta() != null ? aula.getPlanta() : 0);
            
            if (aula.getCursoId() != null) {
                stmt.setLong(4, aula.getCursoId());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }
            
            stmt.setLong(5, aula.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM aula WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Aula getById(Long id) throws SQLException {
        Aula aula = null;
        String sql = "SELECT id, nombre, edificio, planta, curso_id FROM aula WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aula = new Aula();
                    aula.setId(rs.getLong("id"));
                    aula.setNombre(rs.getString("nombre"));
                    aula.setEdificio(rs.getString("edificio"));
                    aula.setPlanta(rs.getInt("planta"));
                    aula.setCursoId(rs.getLong("curso_id"));
                }
            }
        }

        return aula;
    }

    // RELACIÓN 1:1 - getByCursoId: Método específico para relaciones 1:1
    // Retorna null si no existe, un objeto si existe (nunca lista porque es 1:1)
    // UNIQUE KEY en BD garantiza que cada curso_id aparece solo una vez
    @Override
    public Aula getByCursoId(Long cursoId) throws SQLException {
        Aula aula = null;
        String sql = "SELECT id, nombre, edificio, planta, curso_id FROM aula WHERE curso_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aula = new Aula();
                    aula.setId(rs.getLong("id"));
                    aula.setNombre(rs.getString("nombre"));
                    aula.setEdificio(rs.getString("edificio"));
                    aula.setPlanta(rs.getInt("planta"));
                    aula.setCursoId(rs.getLong("curso_id"));
                }
            }
        }

        return aula;
    }

    @Override
    public List<Aula> listCursos() throws SQLException {
        String sql = "SELECT id, nombre FROM curso";
        List<Aula> cursos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aula aux = new Aula();
                aux.setId(rs.getLong("id"));
                aux.setNombre(rs.getString("nombre"));
                cursos.add(aux);
            }
        }
        return cursos;
    }
}
