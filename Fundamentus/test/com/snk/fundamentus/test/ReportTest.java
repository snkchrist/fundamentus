package com.snk.fundamentus.test;

import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;
import com.snk.fundamentus.report.ReportFundamentalista;
import com.snk.fundamentus.report.ReportUtil;


public class ReportTest {

    @Test
    public void test() {
        ReportFundamentalista f = new ReportFundamentalista(null);

        Calendar ultimoTrimestre = ReportUtil.getUltimoTrimestre(Calendar.getInstance());

        Assert.assertNotNull(ultimoTrimestre);
        Assert.assertEquals(11, ultimoTrimestre.get(Calendar.MONTH));
        Assert.assertEquals(2014, ultimoTrimestre.get(Calendar.YEAR));
        Assert.assertEquals(31, ultimoTrimestre.get(Calendar.DAY_OF_MONTH));

    }

}
