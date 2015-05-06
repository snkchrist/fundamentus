package com.snk.fundamentus.interfaces;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

public interface ITelaPrincipal {
    public String getTextSearch();

    public void setTblDetalhesBasicosModel(final TableModel tblModel);

    public void setTblAcoesModel(final TableModel tblModel);

    public Object getSelectedObjectAtTblAcoes(final int row, final int column);

    public int getSelectedRowAtTblAcoes();

    public int getSelectedColumnAtTblAcoes();

    void addNewBalancoTab(String tabName, JPanel panel);

    void addNewDemonstrativoTab(String tabName, JPanel panel);

    JPanel getJPanelTemplateToBalancoTab(TableModel tblModel);

    void clearBalancoTab();

}
