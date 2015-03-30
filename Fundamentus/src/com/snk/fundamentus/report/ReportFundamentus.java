package com.snk.fundamentus.report;

import java.util.List;

import org.apache.log4j.Logger;

import com.snk.fundamentus.enums.MetodoInvestimento;
import com.snk.fundamentus.models.Empresa;

public class ReportFundamentus {

    private static final Logger logger = Logger.getLogger(ReportFundamentus.class);

    public static void main(final String[] args) {
        GuruMethod guru = GuruMethod.getGurusMethodInstance(MetodoInvestimento.Grahan, 2014);
        List<Empresa> lstEmpresa = guru.getLstEmpresa();

        for (Empresa empresa : lstEmpresa) {
            logger.info("Empresa [" + empresa.getSigla() + "] se enquadra no método Graham.");
        }

    }

    private static int getGeneralReport(int count, final Empresa empresa) {
        ReportFundamentalista report = new ReportFundamentalista(empresa, 2014);
        double lpa = report.getLPA();
        double pl = report.getPL();
        double pvpa = report.getPVPA();
        double vpa = report.getVPA();

        if (true == report.teveLucroUltimos32Semestres()) {
            int ano = 2014;

            count++;
            logger.info("Empresa [" + empresa.getSigla()
                    + "] teve lucro nos últimos 32 semestres");

            logger.info("####################LIQUIDEZ####################");
            logger.info("Liquidez Corrente ano 2014: [" + report.getLiquidezCorrentePorAno() + "]");
            logger.info("Liquidez Geral ano 2014: [" + report.getLiquidezGeral() + "]");
            logger.info("Liquidez Imediata ano 2014: [" + report.getLiquidezImediata() + "]");
            logger.info("Liquidez Seca ano 2014: [" + report.getLiquidezSeca() + "]");
            logger.info("########################################");

            logger.info("Indice de Lucratividade ano 2014:[" + report.getIndiceLucratividade() + "]");
            logger.info("Indice de Rotatividade ano 2014:[" + report.getIndiceRotatividade() + "]");

        }
        return count;
    }
}
