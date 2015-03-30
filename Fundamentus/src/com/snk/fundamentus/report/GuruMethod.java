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
         * - Vendas anuais substanciais: acima de US$250 milhões.
         * - Sólida posição financeira: Liquidez Corrente de pelo menos 1,00 e
         * relação Dívida Líqüida/Patrimônio Líquido máxima de 50%.
         * - Estabilidade de lucro: lucro e dividendos contínuos durante 8 anos.
         * - Crescimento de lucro: aumento de pelo menos 4% a.a. usando as
         * médias dos 3 anos no início e no fim do período.
         * - Preço/Lucro histórico moderado: o preço atual da ação individual,
         * dividido pelo lucro médio dos últimos 3 anos (em reais), não deve
         * exceder 15x. A recomendação básica de Graham é que o Lucro/Preço
         * médio (o inverso do Preço/Lucro médio) da carteira seja pelo menos
         * igual à taxa de juros oferecida por bônus de boa qualidade.
         * - Preço/Patrimônio Líquido moderado: não deve ultrapassar 150% (mas,
         * seguindo Graham, aceitamos um Preço/Patrimônio Líquido maior de 150%
         * (ou 1,5) se o produto de Preço/Patrimônio Liquido x Preço/Lucro é
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
