package com.snk.fundamentus.database;

import com.snk.fundamentus.models.Empresa;

public class DaoFactory {

    public Database<Empresa> getEmpresaDao() {
        return new Database<Empresa>(Empresa.class);
    }
}
