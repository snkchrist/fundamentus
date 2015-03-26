package com.snk.fundamentus.database;


public class DaoFactory {

    public EmpresaDao getEmpresaDao() {
        return new EmpresaDao();
    }
}
