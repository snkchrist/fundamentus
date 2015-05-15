package com.snk.fundamentus.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jxl.common.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.models.Empresa;

public class GuruMethod {

    private final DaoFactory daoFactory = new DaoFactory();
    private final List<Empresa> lstEmpresa;
    private final Logger logger = Logger.getLogger(GuruMethod.class);

    private GuruMethod() {
        lstEmpresa = new ArrayList<Empresa>();
    }

    public List<Empresa> getLstEmpresa() {
        return lstEmpresa;
    }

    public static GuruMethod getGurusMethodInstance(
            final MetodoInvestimento method,
            final Integer ano) {
        GuruMethod guru = null;
        int anoCorrente;

        if (null == ano) {
            anoCorrente = Calendar.getInstance().get(Calendar.YEAR) - 1;
        }
        else {
            anoCorrente = ano;
        }

        if (MetodoInvestimento.Grahan.equals(method)) {
            guru = new GuruMethod();
            guru.buildGrahansMethod(anoCorrente);
        }
        else if (MetodoInvestimento.Ultimos5AP.equals(method)) {
            guru = new GuruMethod();
            guru.buildLast5PositiveYeards();
        }

        return guru;
    }

    private void buildLast5PositiveYeards() {
        List<Empresa> empresaList = daoFactory.getEmpresaDao().listAllElements();

        for (Empresa empresa : empresaList) {
            ReportFundamentalista report = new ReportFundamentalista(empresa);

            if (true == report.teveLucroAcoesUltimos5Anos()) {
                getLstEmpresa().add(empresa);
            }
        }
    }

    private void buildGrahansMethod(final int ano) {

        List<Empresa> empresaList = daoFactory.getEmpresaDao().listAllElements();

        for (Empresa empresa : empresaList) {
            ReportFundamentalista report = new ReportFundamentalista(empresa, ano);
            if (report.isGrahansMethod()) {
                empresaList.add(empresa);
            }
        }
    }
}
