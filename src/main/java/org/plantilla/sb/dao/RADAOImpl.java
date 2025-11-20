package org.plantilla.sb.dao;

import org.plantilla.sb.entities.RA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RADAOImpl implements RADAO {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<RA> listAllRA() throws SQLException {
        List<RA> resultados = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, curso_id FROM ra";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RA ra = new RA();
                ra.setId(rs.getLong("id"));
                ra.setNombre(rs.getString("nombre"));
                ra.setDescripcion(rs.getString("descripcion"));
                ra.setCursoId(rs.getLong("curso_id"));
                resultados.add(ra);
            }
        }

        return resultados;
    }

    @Override
    public void insertRA(RA ra) throws SQLException {
        String sql = "INSERT INTO ra (nombre, descripcion, curso_id) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ra.getNombre());
            stmt.setString(2, ra.getDescripcion());

            if (ra.getCursoId() != null) {
                stmt.setLong(3, ra.getCursoId());
            } else {
                stmt.setNull(3, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateRA(RA ra) throws SQLException {
        String sql = "UPDATE ra SET nombre = ?, descripcion = ?, curso_id = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ra.getNombre());
            stmt.setString(2, ra.getDescripcion());

            if (ra.getCursoId() != null) {
                stmt.setLong(3, ra.getCursoId());
            } else {
                stmt.setNull(3, java.sql.Types.BIGINT);
            }

            stmt.setLong(4, ra.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteRA(Long id) throws SQLException {
        String sql = "DELETE FROM ra WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public RA getRAById(Long id) throws SQLException {
        RA ra = null;
        String sql = "SELECT id, nombre, descripcion, curso_id FROM ra WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ra = new RA();
                    ra.setId(rs.getLong("id"));
                    ra.setNombre(rs.getString("nombre"));
                    ra.setDescripcion(rs.getString("descripcion"));
                    ra.setCursoId(rs.getLong("curso_id"));
                }
            }
        }

        return ra;
    }

    @Override
    public List<RA> getRAByCursoId(Long cursoId) throws SQLException {
        List<RA> resultados = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, curso_id FROM ra WHERE curso_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cursoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RA ra = new RA();
                    ra.setId(rs.getLong("id"));
                    ra.setNombre(rs.getString("nombre"));
                    ra.setDescripcion(rs.getString("descripcion"));
                    ra.setCursoId(rs.getLong("curso_id"));
                    resultados.add(ra);
                }
            }
        }

        return resultados;
    }
}
