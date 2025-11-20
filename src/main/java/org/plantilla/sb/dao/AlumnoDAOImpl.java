package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Alumno;
import org.plantilla.sb.entities.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlumnoDAOImpl implements AlumnoDAO {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Alumno> listAllAlumnos() throws SQLException {
        String sql = "SELECT * FROM alumno";
        List<Alumno> alumnos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno a = new Alumno();
                a.setId(rs.getLong("id"));
                a.setNombre(rs.getString("nombre"));
                a.setEmail(rs.getString("email"));
                alumnos.add(a);
            }
        }

        return alumnos;
    }

    @Override
    public void insertAlumno(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumno (nombre, email) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getEmail());

            ps.executeUpdate();
        }
    }

    @Override
    public void updateAlumno(Alumno alumno) throws SQLException {
        String sql = "UPDATE alumno SET nombre=?, email=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getEmail());
            ps.setLong(3, alumno.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAlumno(Long id) throws SQLException {
        String sql = "DELETE FROM alumno WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Alumno getAlumnoById(Long id) throws SQLException {
        String sql = "SELECT * FROM alumno WHERE id=?";
        Alumno alumno = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alumno = new Alumno();
                    alumno.setId(rs.getLong("id"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setEmail(rs.getString("email"));
                }
            }
        }

        return alumno;
    }

    @Override
    public List<Curso> getCursosByAlumnoId(Long alumnoId) throws SQLException {
        String sql = """
                SELECT c.id, c.nombre
                FROM curso c
                JOIN alumno_curso ac ON c.id = ac.curso_id
                WHERE ac.alumno_id = ?
                """;

        List<Curso> cursos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, alumnoId);

            try (ResultSet rs = ps.executeQuery()) {
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

    @Override
    public void addCursoToAlumno(Long alumnoId, Long cursoId) throws SQLException {
        String sql = "INSERT INTO alumno_curso (alumno_id, curso_id) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, alumnoId);
            ps.setLong(2, cursoId);

            ps.executeUpdate();
        }
    }

    @Override
    public void removeCursoFromAlumno(Long alumnoId, Long cursoId) throws SQLException {
        String sql = "DELETE FROM alumno_curso WHERE alumno_id=? AND curso_id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, alumnoId);
            ps.setLong(2, cursoId);

            ps.executeUpdate();
        }
    }
}

