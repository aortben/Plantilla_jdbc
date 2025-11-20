package org.plantilla.sb.dao;

import org.plantilla.sb.entities.Aula;
import java.sql.SQLException;
import java.util.List;

public interface AulaDAO {
    List<Aula> list() throws SQLException;
    void insert(Aula aula) throws SQLException;
    void update(Aula aula) throws SQLException;
    void delete(Long id) throws SQLException;
    Aula getById(Long id) throws SQLException;
    Aula getByCursoId(Long cursoId) throws SQLException;
    List<Aula> listCursos() throws SQLException;
}
