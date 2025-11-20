package org.plantilla.sb.dao;

import org.plantilla.sb.entities.RA;

import java.sql.SQLException;
import java.util.List;

public interface RADAO {

    List<RA> list() throws SQLException;

    void insert(RA ra) throws SQLException;

    void update(RA ra) throws SQLException;

    void delete(Long id) throws SQLException;

    RA getById(Long id) throws SQLException;

    List<RA> getRelacionados(Long cursoId) throws SQLException;
}
