package com.snk.fundamentus.utils.insert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;

import com.snk.fundamentus.database.DaoFactory;
import com.snk.fundamentus.database.EmpresaDao;
import com.snk.fundamentus.models.BalancoPatrimonial;
import com.snk.fundamentus.models.DemonstrativoResultado;
import com.snk.fundamentus.models.Empresa;
import com.snk.fundamentus.utils.BalancoComparator;
import com.snk.fundamentus.utils.DemonstrativoComparator;
import com.snk.fundamentus.utils.transform.BalancoXlsTranslator;
import com.snk.fundamentus.utils.transform.DemonstrativoXlsTranslator;

public class ImportXlsHelper {

    private final static Logger logger = Logger.getLogger(ImportXlsHelper.class);
    private final DaoFactory daoFactory = new DaoFactory();

    public static void main(final String[] args)
            throws IOException, ParseException, BiffException {

        ImportXlsHelper reader = new ImportXlsHelper();
        reader.importXLSDataIntoDb();

    }

    private void importXLSDataIntoDb()
            throws FileNotFoundException, IOException, ParseException, BiffException {
        String path = "C:/Users/ribeifil/Desktop/xlsFiles/xls";
        addMissingBalancos(path);
    }

    /**
     * insert into db the missing balancos/demonstrativos grabbed form xls files
     *
     * @param folderXlsFiles
     * @throws IOException
     * @throws BiffException
     */
    private void addMissingBalancos(final String folderXlsFiles)
            throws IOException, BiffException {
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        File file = new File(folderXlsFiles);

        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();

            for (File fileName : listFiles) {
                BalancoXlsTranslator balancoTranslator = new BalancoXlsTranslator();
                DemonstrativoXlsTranslator demonstrativoTranslator = new DemonstrativoXlsTranslator();

                jxl.Workbook workbook = jxl.Workbook.getWorkbook(fileName);

                List<BalancoPatrimonial> decodeBalanco = balancoTranslator.decodeXsl(workbook);
                List<DemonstrativoResultado> decodeDemonstrativo = demonstrativoTranslator.decodeXsl(workbook);

                workbook.close();

                String fileNameWithoutExtention = fileName.getName().replaceFirst("[.][^.]+$", "");
                Empresa empresa = empresaDao.findEmpresaBySigla(fileNameWithoutExtention);

                empresaDao.getEm().getTransaction().begin();

                updateBalancoList(decodeBalanco, empresa);
                updateDemonstrativoList(decodeDemonstrativo, empresa);
                sortCollections(empresa);

                empresaDao.getEm().getTransaction().commit();
            }
        }
    }

    private void sortCollections(final Empresa empresa) {
        Collections.sort(empresa.getBalancoList(), new BalancoComparator());
        Collections.reverse(empresa.getBalancoList());
        Collections.sort(empresa.getDemonstrativoList(), new DemonstrativoComparator());
        Collections.reverse(empresa.getDemonstrativoList());
    }

    private void updateBalancoList(final List<BalancoPatrimonial> decodeBalanco, final Empresa empresa) {
        for (BalancoPatrimonial balancoPatrimonial : decodeBalanco) {

            if (false == empresa.containsBalancoByDate(balancoPatrimonial.getDataDoBalanco())) {
                logger.info("Adding balanco da data ["
                        + balancoPatrimonial.getDataDoBalanco()
                        + "] na empresa ["
                        + empresa.getSigla()
                        + "]");

                empresa.getBalancoList().add(balancoPatrimonial);
            }
        }
    }

    private void updateDemonstrativoList(final List<DemonstrativoResultado> decodeDemonstrativo, final Empresa empresa) {
        for (DemonstrativoResultado demonstrativo : decodeDemonstrativo) {

            if (false == empresa.containsDemonstrativoByDate(demonstrativo.getDataDemonstrativo())) {
                logger.info("Adding balanco da data ["
                        + demonstrativo.getDataDemonstrativo()
                        + "] na empresa ["
                        + empresa.getSigla()
                        + "]");

                empresa.getDemonstrativoList().add(demonstrativo);
            }
        }
    }

    private void addMissingDemonstrativos(final String folderXlsFiles)
            throws IOException, BiffException {
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        File file = new File(folderXlsFiles);

        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();

            for (File fileName : listFiles) {
                BalancoXlsTranslator translator = new BalancoXlsTranslator();
                jxl.Workbook workbook = jxl.Workbook.getWorkbook(fileName);

                List<BalancoPatrimonial> decodeXsl = translator.decodeXsl(workbook);

                String fileNameWithoutExtention = fileName.getName().replaceFirst("[.][^.]+$", "");
                Empresa empresa = empresaDao.findEmpresaBySigla(fileNameWithoutExtention);

                empresaDao.getEm().getTransaction().begin();
                updateBalancoList(decodeXsl, empresa);
                Collections.sort(empresa.getBalancoList(), new BalancoComparator());
                empresaDao.getEm().getTransaction().commit();
            }
        }
    }

    /***
     * Sort decending the balanco list and saves into the db
     */
    private void sortAllBalancoList() {
        EmpresaDao empresaDao = daoFactory.getEmpresaDao();

        List<Empresa> listAllElements = empresaDao.listAllElements();

        for (Empresa empresa : listAllElements) {
            empresaDao.getEm().getTransaction().begin();

            List<BalancoPatrimonial> balancoList = empresa.getBalancoList();

            Collections.sort(balancoList, new BalancoComparator());
            Collections.reverse(balancoList);

            empresaDao.getEm().getTransaction().commit();
        }
    }
}
