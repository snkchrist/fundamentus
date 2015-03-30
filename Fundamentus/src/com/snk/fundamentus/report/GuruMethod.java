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

    public static GuruMethod getGurusMethodInstance(final MetodoInvestimento method, final int ano) {
        GuruMethod guru = null;

        if (MetodoInvestimento.Grahan.equals(method)) {
            guru = new GuruMethod();
            guru.buildGrahansMethod(ano);
        }

        return guru;
    }

    private List<Empresa> buildGrahansMethod(final int ano) {
        /*
         * - Companhias proeminentes em seus setores.
         * - Vendas anuais substanciais: acima de US$250 milh�es.
         * - S�lida posi��o financeira: Liquidez Corrente de pelo menos 1,00 e
         * rela��o D�vida L�q�ida/Patrim�nio L�quido m�xima de 50%.
         * - Estabilidade de lucro: lucro e dividendos cont�nuos durante 8 anos.
         * - Crescimento de lucro: aumento de pelo menos 4% a.a. usando as
         * m�dias dos 3 anos no in�cio e no fim do per�odo.
         * - Pre�o/Lucro hist�rico moderado: o pre�o atual da a��o individual,
         * dividido pelo lucro m�dio dos �ltimos 3 anos (em reais), n�o deve
         * exceder 15x. A recomenda��o b�sica de Graham � que o Lucro/Pre�o
         * m�dio (o inverso do Pre�o/Lucro m�dio) da carteira seja pelo menos
         * igual � taxa de juros oferecida por b�nus de boa qualidade.
         * - Pre�o/Patrim�nio L�quido moderado: n�o deve ultrapassar 150% (mas,
         * seguindo Graham, aceitamos um Pre�o/Patrim�nio L�quido maior de 150%
         * (ou 1,5) se o produto de Pre�o/Patrim�nio Liquido x Pre�o/Lucro �
         * menos de 22,50).
         */

        List<Empresa> empresaList = daoFactory.getEmpresaDao().listAllElements();

        for (Empresa empresa : empresaList) {
            ReportFundamentalista report = new ReportFundamentalista(empresa, ano);
            boolean vendasSubstanciais = report.getVendasPorAno() > 250000000;
            boolean liquidezCorrente = report.getLiquidezCorrentePorAno() >= 1;

            if (vendasSubstanciais && liquidezCorrente) {
                getLstEmpresa().add(empresa);
            }

        }

        return null;

    }
}
