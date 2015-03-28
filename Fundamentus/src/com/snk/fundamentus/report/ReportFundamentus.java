package com.snk.fundamentus.report;

import java.util.List;
import org.apache.log4j.Logger;
import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.models.Empresa;


public class ReportFundamentus {

    private static final Logger logger = Logger.getLogger(ReportFundamentus.class);

    public static void main(final String[] args) {
        DaoFactory daoFactory = new DaoFactory();
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        List<Empresa> listAllElements = empresaDao.listAllElements();

        int count = 0;

        for (Empresa empresa : listAllElements) {
            ReportFundamentalista report = new ReportFundamentalista(empresa);
            double lpa = report.getLPA();
            double pl = report.getPL();
            double pvpa = report.getPVPA();
            double vpa = report.getVPA();

            if (true == report.teveLucroUltimos32Semestres()) {
                count++;
                logger.info("Empresa [" + empresa.getSigla()
                    + "] teve lucro nos últimos 32 semestres");

                double liquidezCorrenteAno2014 = report.getLiquidezCorrentePorAno(2014);

                if (liquidezCorrenteAno2014 != -1) {
                    logger.info("Liquidez corrente ano 2014: [" + liquidezCorrenteAno2014
                        + "]");
                }
            }

        }
    }
}
