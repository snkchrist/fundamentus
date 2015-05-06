package com.snk.fundamentus.ui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import com.snk.fundamentus.controller.PrincipalController;
import com.snk.fundamentus.interfaces.ITelaPrincipal;

public class TelaPrincipal implements ITelaPrincipal {

    private JFrame frmStocksearch;
    private JTable tblAcoes;
    private JTextField txtSearch;
    private JScrollPane scrollPane;
    private PrincipalController controller;
    private JTable tblDetalhesBasicos;
    private JTabbedPane tabbedPaneBalancos;
    private JTabbedPane tabbedPaneDemonstrativos;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

                    TelaPrincipal window = new TelaPrincipal();
                    window.frmStocksearch.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public TelaPrincipal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmStocksearch = new JFrame();
        frmStocksearch.setTitle("StockSearch");
        frmStocksearch.setBounds(100, 100, 685, 577);
        frmStocksearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmStocksearch.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

        controller = new PrincipalController(this);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setToolTipText("");
        frmStocksearch.getContentPane().add(tabbedPane, "cell 0 0,grow");

        JPanel panel = new JPanel();
        tabbedPane.addTab("Principal", null, panel, null);
        panel.setLayout(new MigLayout("", "[300px][grow][grow]", "[][grow]"));

        txtSearch = new JTextField();
        panel.add(txtSearch, "cell 0 0 2 1,hmax 25px,grow");

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(controller.getBtnBuscarActionListener());

        panel.add(btnBuscar, "cell 2 0,alignx right");

        JPanel pnlTable = new JPanel();

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tblAcoes = new JTable();

        if (null != controller.getStockModel(null)) {
            tblAcoes = new JTable(controller.getStockModel(null));
        }
        tblAcoes.addMouseListener(controller.getTblAcoesMouseAdapter());
        tblAcoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pnlTable.setPreferredSize(tblAcoes.getPreferredSize());
        scrollPane.add(pnlTable);
        scrollPane.setViewportView(pnlTable);

        panel.add(scrollPane, "cell 0 1,wmin 300,growy");
        pnlTable.setLayout(new MigLayout("", "[400px]", "[][grow]"));
        pnlTable.add(tblAcoes.getTableHeader(), "cell 0 0,growx, hmax 20, aligny top, wrap");
        pnlTable.add(tblAcoes, "cell 0 1,growx,aligny top");

        JPanel pnlDetalhes = new JPanel();
        pnlDetalhes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        pnlDetalhes.setLayout(new MigLayout("", "[grow]", "[grow]"));
        panel.add(pnlDetalhes, "cell 1 1 2 1,grow");
        //pnlDetalhes.setLayout(new MigLayout("", "[grow]", "[][grow]"));

        JTabbedPane tabbedPaneAcao = new JTabbedPane(JTabbedPane.TOP);
        tabbedPaneAcao.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlDetalhes.add(tabbedPaneAcao, "grow");

        JPanel pnlDetalhesBasicos = new JPanel();
        tabbedPaneAcao.addTab("Detalhes", null, pnlDetalhesBasicos, null);
        pnlDetalhesBasicos.setLayout(new MigLayout("", "[grow, fill]", "[grow, fill]"));

        tblDetalhesBasicos = new JTable();
        pnlDetalhesBasicos.add(tblDetalhesBasicos, "cell 0 0,grow");

        JPanel pnlBalancos = new JPanel();
        tabbedPaneAcao.addTab("Balanços", null, pnlBalancos, null);
        pnlBalancos.setLayout(new MigLayout("", "[grow]", "[grow]"));

        tabbedPaneBalancos = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPaneBalancos.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pnlBalancos.add(tabbedPaneBalancos, "cell 0 0,grow");

        JPanel pnlDemonstrativos = new JPanel();
        tabbedPaneAcao.addTab("Demonstrativos", null, pnlDemonstrativos, null);
        pnlDemonstrativos.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

        tabbedPaneDemonstrativos = new JTabbedPane(JTabbedPane.BOTTOM);
        pnlDemonstrativos.add(tabbedPaneDemonstrativos, "cell 0 0,grow");

        JButton btnAvancado = new JButton("Avan\u00E7ado");
        panel.add(btnAvancado, "cell 2 0,wmax 90,alignx right");
    }

    @Override
    public void addNewBalancoTab(final String tabName, final JPanel panel) {
        tabbedPaneBalancos.addTab(tabName, null, panel, null);
    }

    @Override
    public void addNewDemonstrativoTab(final String tabName, final JPanel panel) {
        tabbedPaneDemonstrativos.addTab(tabName, null, panel, null);
    }

    @Override
    public void clearBalancoTab() {
        tabbedPaneBalancos.removeAll();
    }

    @Override
    public JPanel getJPanelTemplateToBalancoTab(final TableModel tblModel) {
        JPanel pnlTemplate = new JPanel();
        pnlTemplate.setLayout(new MigLayout("", "[grow, fill]", "[grow, fill]"));

        JTable tblTemplate = new JTable(tblModel);
        pnlTemplate.add(tblTemplate, "cell 0 0,grow");

        return pnlTemplate;
    }

    @Override
    public String getTextSearch() {
        return txtSearch.getText();
    }

    @Override
    public void setTblDetalhesBasicosModel(final TableModel tblModel) {
        tblDetalhesBasicos.setModel(tblModel);
        tblDetalhesBasicos.repaint();
        tblDetalhesBasicos.revalidate();
    }

    @Override
    public void setTblAcoesModel(final TableModel tblModel) {
        tblAcoes.setModel(tblModel);
        tblAcoes.repaint();
        tblAcoes.revalidate();
    }

    @Override
    public int getSelectedRowAtTblAcoes() {
        return tblAcoes.getSelectedRow();
    }

    @Override
    public int getSelectedColumnAtTblAcoes() {
        return tblAcoes.getSelectedColumn();
    }

    @Override
    public Object getSelectedObjectAtTblAcoes(final int row, final int column) {
        return tblAcoes.getValueAt(row, column);
    }
}
