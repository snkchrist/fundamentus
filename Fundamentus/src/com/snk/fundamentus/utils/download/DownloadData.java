package com.snk.fundamentus.utils.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.Database;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.enums.EmpEnum;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.utils.transform.HtmlConverter;

public class DownloadData {
    private static final String SESSION_ID_URL = "ff71jkjnk8crugs0n1l5i6l1g7";
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
        data.updateEmpresaInformation();

        //   data.downloadXlsFiles(destFolder);
        //
        //        File files = new File(destFolder);
        //        if (files.isDirectory()) {
        //            File[] listFiles = files.listFiles();
        //
        //            for (File file : listFiles) {
        //                data.getZipFiles(file, zipDestFolder);
        //            }
        //        }
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
        HttpEntity download = download(url);
        String string = EntityUtils.toString(download);
        download.consumeContent();

        return string;
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

        System.out.println("DownloadedCompleted");
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
            System.out.println("Extracting: " + entry.getName());
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

    public void updateEmpresaInformation()
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
}
