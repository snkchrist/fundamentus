package com.snk.fundamentus.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jxl.common.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.models.Empresa;

public class GuruMethod {

    private DaoFactory daoFactory;
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
            anoCorrente = Calendar.getInstance().get(Calendar.YEAR);
        }
        else {
            anoCorrente = ano;
        }

        guru = new GuruMethod();
        guru.buildMethod(method, anoCorrente);

        return guru;
    }

    private DaoFactory getDaoFactory() {
        if (null == daoFactory) {
            daoFactory = new DaoFactory();
        }

        return daoFactory;
    }

    private void buildMethod(final MetodoInvestimento metodo, final int ano) {
        List<Empresa> empresaList = getDaoFactory().getEmpresaDao().listAllElements();

        for (Empresa empresa : empresaList) {
            ReportFundamentalista report = new ReportFundamentalista(empresa, ano);

            if (MetodoInvestimento.Rentabilidade100Porcento5Anos.equals(metodo)) {
                if (report.getRentabilidadeAcumuladaUltimos5Anos() > 100) {
                    getLstEmpresa().add(empresa);
                }
            }

            else if (MetodoInvestimento.QdeTobin.equals(metodo)) {
                if (report.getQdeTobinUltimoTrimestre() > 1) {
                    getLstEmpresa().add(empresa);
                }
            }

            else if (MetodoInvestimento.PositivoUltimos5Anos.equals(metodo)) {
                if (true == report.teveAumentoRentabilidadeUltimos5Anos()) {
                    getLstEmpresa().add(empresa);
                }
            }

            else if (MetodoInvestimento.Grahan.equals(metodo)) {
                if (report.isGrahansMethod()) {
                    getLstEmpresa().add(empresa);
                }
            }

            else if (MetodoInvestimento.VPAMaiorQueValorDaAcao.equals(metodo)) {
                if (report.isVPAMaiorQueCotacao()) {
                    getLstEmpresa().add(empresa);
                }
            }

            else if (MetodoInvestimento.Filipe.equals(metodo)) {
                if (report.isFilipesMethod()) {
                    getLstEmpresa().add(empresa);
                }
            }
        }
    }

}
