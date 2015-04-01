package com.snk.fundamentus.report;

import java.util.ArrayList;
import java.util.List;
import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.models.Empresa;


public class GuruMethod {

    private final DaoFactory daoFactory = new DaoFactory();
    private final List<Empresa> lstEmpresa;

    private GuruMethod() {
        lstEmpresa = new ArrayList<Empresa>();
    }

    public List<Empresa> getLstEmpresa() {
        return lstEmpresa;
    }

    public static GuruMethod getGurusMethodInstance(
        final MetodoInvestimento method,
        final int ano) {
        GuruMethod guru = null;

        if (MetodoInvestimento.Grahan.equals(method)) {
            guru = new GuruMethod();
            guru.buildGrahansMethod(ano);
        }

        return guru;
    }

    private List<Empresa> buildGrahansMethod(final int ano) {

        List<Empresa> empresaList = daoFactory.getEmpresaDao().listAllElements();

        for (Empresa empresa : empresaList) {
            ReportFundamentalista report = new ReportFundamentalista(empresa, ano);
            boolean vendasSubstanciais = report.getVendasPorAno() > 250000000;
            boolean liquidezCorrente = report.getLiquidezCorrentePorAno() >= 1;
            boolean relacaoDividaPatrimonio = report.getRelacaoDividaLiquidaPatrimonioLiquido() > 0.5;
            boolean teveLucroUltimos32Semestres = report.teveLucroUltimos32Semestres();

            if (vendasSubstanciais && liquidezCorrente && relacaoDividaPatrimonio
                && teveLucroUltimos32Semestres) {
                getLstEmpresa().add(empresa);
            }

        }

        return null;

    }
}
