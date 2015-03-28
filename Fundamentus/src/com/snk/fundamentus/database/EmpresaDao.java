package com.snk.fundamentus.database;

import javax.persistence.TypedQuery;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;


public class EmpresaDao extends Database<Empresa> {

    public EmpresaDao() {
        super(Empresa.class);
    }

    public Empresa findListBySigla(final String sigla) {
        TypedQuery createQuery = getEm().createQuery("SELECT p FROM "
            + type.getSimpleName() + " p WHERE p.sigla = '" + sigla + "'", type);
        return (Empresa) createQuery.getSingleResult();
    }

}
