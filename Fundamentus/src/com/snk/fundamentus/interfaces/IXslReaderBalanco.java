package com.snk.fundamentus.interfaces;

import java.util.List;

import jxl.Workbook;

public interface IXslReaderBalanco<T> {
    public List<T> decodeXsl(Workbook workbook);

    public void translateCell(T source, String columnTitle, String columnValue);
}
