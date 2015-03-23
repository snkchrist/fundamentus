package com.snk.fundamentus;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.Database;
import com.snk.fundamentus.enums.EmpEnum;
import com.snk.fundamentus.models.Empresa;

public class DownloadFundamentusData {

    private HttpClient client;
    private final DaoFactory daoFactory = new DaoFactory();

    public HttpClient getClient() {
        if (null == client) {
            client = new DefaultHttpClient();
            setProxy(client);
        }

        return client;
    }

    private void setProxy(final HttpClient client) {
        final HttpHost proxy = new HttpHost("web-proxy", 8088);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }

    public String downloadHtml(final String url)
            throws ClientProtocolException, IOException {
        final HttpGet getMethod = new HttpGet(url);
        final HttpResponse execute = getClient().execute(getMethod);
        return EntityUtils.toString(execute.getEntity());

    }

    public static void main(final String[] args)
            throws ClientProtocolException, XPathExpressionException, IOException, ParserConfigurationException {
        // new DownloadFundamentusData().getAllInformation();

        final Database<Empresa> base = new Database<Empresa>(Empresa.class);

        final List<Empresa> listAllElements = base.listAllElements();

        for (final Empresa empresa : listAllElements) {

            double osc = empresa.getOscilacoes().getAno2010() +
                    empresa.getOscilacoes().getAno2011() +
                    empresa.getOscilacoes().getAno2012() +
                    empresa.getOscilacoes().getAno2013() +
                    empresa.getOscilacoes().getAno2014() +
                    empresa.getOscilacoes().getAno2015();

            if ((osc / 5) > 13.5) {

                double numeroDeAcoes = empresa.getNumeroDeAcoes();
                double lpa = ((double) empresa.getDemonstracao12meses().getLucroLiquido() / numeroDeAcoes);
                double pl = empresa.getCotacao() / lpa;
                double vpa = (double) empresa.getBalanco().getPatrimonioLiquido() / numeroDeAcoes;
                double pvpa = empresa.getCotacao() / vpa;

                if (pl < 10) {
                    System.out.println("[" + empresa.getSigla() + "]");
                    System.out.println("Nome:" + empresa.getNome());
                    System.out.println("Oscilação nos últimos 5 anos: " + osc);
                    System.out.println("LPA: " + lpa);
                    System.out.println("P/L: " + pl);
                    System.out.println("VPA: " + vpa);
                    System.out.println("P/VPA: " + pvpa);
                }
            }

        }

    }

    public void collectAllInformation()
            throws ClientProtocolException, IOException, XPathExpressionException,
            ParserConfigurationException {
        final String url = "http://fundamentus.com.br/detalhes.php?papel=";

        final EmpEnum[] values = EmpEnum.values();
        final Database<Empresa> base = daoFactory.getEmpresaDao();

        for (final EmpEnum empEnum : values) {
            final String downloadHtml = downloadHtml(url + empEnum.name());
            final HtmlConverter obj = new HtmlConverter(downloadHtml);
            base.add(obj.getEmpresa());
        }

    }
}
