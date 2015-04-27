package com.snk.fundamentus.database;

import java.util.List;

import javax.persistence.TypedQuery;

import com.snk.fundamentus.models.Empresa;

public class EmpresaDao extends Database<Empresa> {

    public EmpresaDao() {
        super(Empresa.class);
    }

    public Empresa findEmpresaBySigla(final String sigla) {
        Empresa result = null;
        TypedQuery createQuery = getEm().createQuery("SELECT p FROM Empresa p WHERE p.sigla = '" + sigla + "'", Empresa.class);
        List<Empresa> resultList = createQuery.getResultList();

        if (null != resultList && resultList.size() > 0) {
            result = (Empresa) createQuery.getResultList().get(0);
        }

        return result;
    }
}
