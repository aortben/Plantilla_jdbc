package org.plantilla.sb.dao;

import org.plantilla.sb.entities.RA;

import java.sql.SQLException;
import java.util.List;

public interface RADAO {

    List<RA> listAllRA() throws SQLException;

    void insertRA(RA ra) throws SQLException;

    void updateRA(RA ra) throws SQLException;

    void deleteRA(Long id) throws SQLException;

    RA getRAById(Long id) throws SQLException;

    List<RA> getRAByCursoId(Long cursoId) throws SQLException;
}
