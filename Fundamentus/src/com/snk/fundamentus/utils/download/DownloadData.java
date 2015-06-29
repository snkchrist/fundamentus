package com.snk.fundamentus.utils.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.snk.fundamentus.XpathClasses;
import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.Database;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.enums.EmpEnum;
import com.snk.fundamentus.models.Cotacao;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.report.ReportUtil;
import com.snk.fundamentus.utils.transform.HtmlConverter;

public class DownloadData {
    private static final String SESSION_ID_URL = "ruu5a48jbv33d9s5v4b1ck4ml3";
    private static final String URL_ZIP = "http://www.fundamentus.com.br/planilhas.php?SID={0}";
    private static final String URL_BALANCO = "http://www.fundamentus.com.br/balancos.php?papel={0}&tipo=1";
    private static final String DEST_ZIP_FOLDER = "C:\\Users\\ribeifil\\Desktop\\xlsFiles\\xls\\";
    private static final String DEST_FOLDER = "C:\\Users\\ribeifil\\Desktop\\xlsFiles\\";
    private HttpClient client;
    private final DaoFactory daoFactory = new DaoFactory();
    private final Logger logger = Logger.getLogger(DownloadData.class);

    public static void main(final String[] args)
            throws ClientProtocolException, XPathExpressionException, IOException, ParserConfigurationException {
        String destFolder = DEST_FOLDER;
        String zipDestFolder = DEST_ZIP_FOLDER;

        DownloadData data = new DownloadData();
        //data.downloadXlsFiles(destFolder);
        data.collectInformationStockPriceYahoo();

        //unzipFiles(destFolder, zipDestFolder, data);
    }

    private static void unzipFiles(final String destFolder, final String zipDestFolder, final DownloadData data)
            throws IOException {
        File files = new File(destFolder);
        if (files.isDirectory()) {
            File[] listFiles = files.listFiles();

            for (File file : listFiles) {
                if (file.isFile()) {
                    data.getZipFiles(file, zipDestFolder);
                }
            }
        }
    }

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

    public HttpEntity download(final String url)
            throws ClientProtocolException, IOException {
        final HttpGet getMethod = new HttpGet(url);
        final HttpResponse execute = getClient().execute(getMethod);

        HttpEntity entity = execute.getEntity();
        return entity;

    }

    public String downloadHtml(final String url)
            throws ClientProtocolException, IOException {
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder builder = new StringBuilder();

        HttpEntity download = download(url);

        InputStream content = download.getContent();

        try {
            br = new BufferedReader(new InputStreamReader(content));

            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ioe) {
            }
        }

        return builder.toString();
    }

    public void downloadXlsFiles(final String destFolder)
            throws ClientProtocolException, IOException {
        String sid = SESSION_ID_URL;
        String balancoUrl = URL_BALANCO;
        String downloadUrl = URL_ZIP;

        String download = java.text.MessageFormat.format(downloadUrl, sid);

        final EmpEnum[] values = EmpEnum.values();

        for (EmpEnum empEnum : values) {
            String firstUrlToAccess = java.text.MessageFormat.format(balancoUrl, empEnum.name());

            String downloadHtml = downloadHtml(firstUrlToAccess);
            HttpEntity entity = download(download);

            if (entity != null) {
                String string = destFolder;

                String fileName = java.text.MessageFormat.format(string + "{0}.rar",
                        empEnum.name());

                FileOutputStream fos = new java.io.FileOutputStream(fileName);
                entity.writeTo(fos);
                fos.close();
            }
        }

        logger.info("DownloadedCompleted");
    }

    private void UnzipFundamentFiles(final File folderToGetFiles, final String folderToExtract)
            throws IOException {
        DownloadData downloadData = new DownloadData();

        File[] listFiles = folderToGetFiles.listFiles();

        for (File file : listFiles) {
            downloadData.getZipFiles(file, folderToExtract);
        }
    }

    /**
     * extract the zip files
     *
     * @param fileName
     * @param destFolder
     * @throws IOException
     */
    public void getZipFiles(final File fileName, final String destFolder)
            throws IOException {
        BufferedOutputStream dest = null;

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(fileName)));

        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {
            logger.info("Extracting: " + entry.getName());

            int count;
            byte data[] = new byte[1024];
            String xlsFileName = "balanco.xls";
            if (entry.isDirectory()) {
                new File(destFolder + "/" + entry.getName()).mkdirs();
                continue;
            }
            else {
                if (entry.getName().equalsIgnoreCase("balanco.xls")) {

                    xlsFileName = destFolder + "/" + fileName.getName().replaceFirst("[.][^.]+$", "") + ".xls";
                }

                int di = xlsFileName.lastIndexOf('/');
                if (di != -1) {
                    new File(destFolder + "/" + xlsFileName).mkdirs();
                }
            }

            FileOutputStream fos = new FileOutputStream(xlsFileName);
            dest = new BufferedOutputStream(fos);

            while ((count = zis.read(data)) != -1) {
                dest.write(data, 0, count);
            }

            dest.flush();
            dest.close();
        }
    }

    public void updateInformation()
            throws ClientProtocolException, IOException, XPathExpressionException,
            ParserConfigurationException {
        final String url = "http://fundamentus.com.br/detalhes.php?papel=";

        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        List<Empresa> listAllElements = empresaDao.listAllElements();

        empresaDao.beginTransaction();
        for (Empresa empresa : listAllElements) {
            final String downloadHtml = downloadHtml(url + empresa.getSigla());
            final HtmlConverter obj = new HtmlConverter(downloadHtml);
            logger.info("Empresa ["
                    + empresa.getSigla()
                    + "] foi atualizada pela última vez em ["
                    + empresa.getDataUltimaCotacao()
                    + "].");
            obj.updateEmpresa(empresa);
            logger.info("Dados atualizados para Empresa ["
                    + empresa.getSigla()
                    + "]. Dados coletados são do dia  ["
                    + empresa.getDataUltimaCotacao()
                    + "].");

        }
        empresaDao.commitTransaction();
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

    public boolean containsCotacao(final Cotacao cotacao, final Empresa empresa) {
        if (null != empresa) {

            List<Cotacao> cotacaoList = empresa.getCotacaoList();

            for (Cotacao cotacao2 : cotacaoList) {
                if (cotacao2.getData().equals(cotacao.getData())) {
                    return true;
                }
            }

        }

        return false;

    }

    public void sortCotacoes() {
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();
        List<Empresa> listAllElements = empresaDao.listAllElements();

        empresaDao.beginTransaction();
        for (Empresa empresa : listAllElements) {
            if (null != empresa.getCotacaoList() && empresa.getCotacaoList().size() > 0) {
                Collections.sort(empresa.getCotacaoList());
                Collections.reverse(empresa.getCotacaoList());
                Cotacao cotacao = empresa.getCotacaoList().get(0);
                empresa.setCotacao(cotacao.getFechamento());
                empresa.setDataUltimaCotacao(ReportUtil.formatDate(cotacao.getData()));

                logger.info("Sort cotacoes para empresa [" + empresa.getNome() + "]");
            }
        }
        empresaDao.commitTransaction();
    }

    public void collectInformationStockPriceYahoo()
            throws ClientProtocolException, IOException, XPathExpressionException, ParserConfigurationException {
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();
        List<Empresa> listAllElements = empresaDao.listAllElements();

        for (Empresa empresa : listAllElements) {

            String url = "http://finance.yahoo.com/q/hp?s=" + empresa.getSigla() + ".SA";
            String downloadHtml = downloadHtml(url);

            String verificationStr = XpathClasses.getFieldFromXpath(
                    downloadHtml,
                    "//*[@id=\"yfi_rt_quote_summary\"]/div[1]/div/span[1]/text()");

            if (verificationStr.contains("Sao Paolo")) {
                String field = XpathClasses.getAttributeFromXPath(
                        downloadHtml,
                        "//*[@id=\"yfncsumtab\"]/tbody/tr[2]/td[1]/p[1]/a",
                        "href");

                if (null != field && false == field.isEmpty()) {
                    String downloadHtml2 = downloadHtml(field);

                    String[] split = downloadHtml2.split("\n");

                    for (int i = 1; i < split.length; i++) {
                        try {
                            String[] split2 = split[i].split(",");

                            Cotacao cotacao = new Cotacao();
                            Date dataCotacao = ReportUtil.formatString(split2[0], "yyyy-MM-dd");

                            cotacao.setData(dataCotacao);
                            cotacao.setAbertura(Double.parseDouble(split2[1]));
                            cotacao.setMaxima(Double.parseDouble(split2[2]));
                            cotacao.setMinima(Double.parseDouble(split2[3]));
                            cotacao.setFechamento(Double.parseDouble(split2[4]));
                            cotacao.setVolume(Long.parseLong(split2[5]));
                            cotacao.setFechamentoAdj(Double.parseDouble(split2[6]));

                            if (false == containsCotacao(cotacao, empresa)) {
                                logger.info("At.. Cotacao empresa ["
                                        + empresa.getSigla()
                                        + "] dia ["
                                        + dataCotacao.toGMTString()
                                        + "]");
                                logger.info("New cotação adicionada para o dia [" + cotacao.getData() + "]");
                                empresaDao.beginTransaction();
                                empresa.getCotacaoList().add(cotacao);
                                empresaDao.commitTransaction();

                            }
                        }
                        catch (Exception e) {
                            logger.error("ERROR", e);
                        }
                    }

                }
                else {
                    logger.info("Não foi possivel baixar cotacoes para empresa [" + empresa.getSigla() + "]");
                }

            }
        }

        sortCotacoes();
    }
}
