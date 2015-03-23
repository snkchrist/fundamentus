package com.snk.fundamentus;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.snk.fundamentus.database.Database;
import com.snk.fundamentus.enums.EmpEnum;
import com.snk.fundamentus.models.Empresa;


public class DownloadFundamentusData {

    private HttpClient client;

    public HttpClient getClient() {
        if (null == client) {
            client = new DefaultHttpClient();
        }

        return client;
    }

    public String downloadHtml(String url) throws ClientProtocolException, IOException {
        HttpGet getMethod = new HttpGet(url);
        HttpResponse execute = getClient().execute(getMethod);
        return EntityUtils.toString(execute.getEntity());

    }

    
    public static void main(String[] args)
        throws ClientProtocolException, XPathExpressionException, IOException, ParserConfigurationException{
        new DownloadFundamentusData().getAllInformation();
    }
    
    public void getAllInformation()
        throws ClientProtocolException, IOException, XPathExpressionException,
        ParserConfigurationException {
        String url = "http://fundamentus.com.br/detalhes.php?papel=";

        EmpEnum[] values = EmpEnum.values();
        Database<Empresa> base = new Database<Empresa>(Empresa.class);

        for (EmpEnum empEnum : values) {
            String downloadHtml = downloadHtml(url + empEnum.name());
            ConvertHtmlResult obj = new ConvertHtmlResult(downloadHtml);
            base.add(obj.getEmpresa());
        }

    }

}
