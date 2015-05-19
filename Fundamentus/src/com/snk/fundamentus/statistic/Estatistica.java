package com.snk.fundamentus.statistic;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.log4j.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.report.LiquidezIndice;
import com.snk.fundamentus.report.ReportFundamentalista;

public class Estatistica {

    private static final Logger logger = Logger.getLogger(Estatistica.class);

    private final DaoFactory daoFactory;

    public Estatistica() {
        daoFactory = new DaoFactory();
    }

    public static void main(final String[] args) {

        Estatistica est = new Estatistica();
        RealMatrix pearsonCorrelationMatrix =
                est.getPearsonCorrelationMatrix();

        logger.info("Correlação entre Rentabilidade e ROIC anual é de [" + pearsonCorrelationMatrix.getEntry(0, 1) + "]");
        logger.info(
                "Correlação entre Rentabilidade e liquidezCorrentePorAno é de [" + pearsonCorrelationMatrix.getEntry(0, 2)
                + "]");
        logger.info("Correlação entre Rentabilidade e vendasPorAno é de [" + pearsonCorrelationMatrix.getEntry(0, 3) + "]");
        logger.info("Correlação entre Rentabilidade e aumentoPercentualLucroLiquidoAnoAAno é de ["
                + pearsonCorrelationMatrix.getEntry(0, 4)
                + "]");

        logger.info("Correlação entre Rentabilidade e aumentoEbitAnoAAno é de [" + pearsonCorrelationMatrix.getEntry(0, 5) + "]");
        logger.info("Correlação entre Rentabilidade e Grahans é de [" + pearsonCorrelationMatrix.getEntry(0, 6) + "]");
        logger.info("Correlação entre Rentabilidade e QdeTobin é de [" + pearsonCorrelationMatrix.getEntry(0, 7) + "]");
        logger.info("Correlação entre Rentabilidade e filipesMethod é de [" + pearsonCorrelationMatrix.getEntry(0, 8) + "]");

    }

    public RealMatrix getPearsonCorrelationMatrix() {
        List<Empresa> listAllElements = daoFactory.getEmpresaDao().listAllElements();

        double[][] matrix = new double[listAllElements.size()][9];

        for (int i = 0; i < listAllElements.size(); i++) {
            Empresa emp = listAllElements.get(i);

            int ano = 2014;
            ReportFundamentalista report = new ReportFundamentalista(emp, ano);
            LiquidezIndice liquidez = new LiquidezIndice(report);

            try {
                double rentabilidadeAno14 = emp.getOscilacoes().getAno2014();
                double roicAno = report.getMediaROICByAno();

                double liquidezCorrentePorAno = liquidez.getMediaLiquidezCorrentePorAno();
                double vendasPorAno = report.getVendasPorAno();
                double aumentoPercentualLucroLiquidoAnoAAno = report.getAumentoPercentualLucroLiquidoAnoAAno();
                double aumentoEbitAnoAAno = report.getAumentoPercentualEbitAnoAAno();
                double grahans = report.isGrahansMethod() == true ? 1000 : -1000;
                double mediaQdeTobinPorAno = report.getMediaQdeTobinPorAno();
                double filipesMethod = report.isFilipesMethodAno() ? 1000 : -1000;

                if (false == isValidNumber(aumentoEbitAnoAAno)
                        || false == isValidNumber(aumentoPercentualLucroLiquidoAnoAAno)) {
                    continue;
                }

                matrix[i][0] = rentabilidadeAno14;
                matrix[i][1] = roicAno;
                matrix[i][2] = liquidezCorrentePorAno;
                matrix[i][3] = vendasPorAno;
                matrix[i][4] = aumentoPercentualLucroLiquidoAnoAAno;
                matrix[i][5] = aumentoEbitAnoAAno;
                matrix[i][6] = grahans;
                matrix[i][7] = mediaQdeTobinPorAno;
                matrix[i][8] = filipesMethod;

            }
            catch (Exception exp) {
                continue;
            }

        }

        PearsonsCorrelation correlation = new PearsonsCorrelation(matrix);
        return correlation.computeCorrelationMatrix(matrix);

    }

    public boolean isValidNumber(final Double db) {
        if (Double.isInfinite(db) || Double.isNaN(db)) {
            return false;
        }
        return true;
    }
}
