package com.snk.fundamentus.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.snk.fundamentus.enums.ShowOnTable;
import com.snk.fundamentus.models.Indices;

public class BuscaAvancada extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtOperationValue;
    private JComboBox cboAttribute;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        try {
            BuscaAvancada dialog = new BuscaAvancada();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public BuscaAvancada() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new MigLayout("", "[grow][][grow]", "[]"));
        {
            cboAttribute = new JComboBox();
            contentPanel.add(cboAttribute, "cell 0 0,growx");
        }
        {
            JComboBox cboOperation = new JComboBox();
            cboOperation.setModel(new DefaultComboBoxModel(new String[] {
                    ">", "<", "=", "<>"
            }));
            contentPanel.add(cboOperation, "cell 1 0");
        }
        {
            txtOperationValue = new JTextField();
            contentPanel.add(txtOperationValue, "flowx,cell 2 0,growx");
            txtOperationValue.setColumns(10);
        }
        {
            JButton btnAddMore = new JButton("+");
            contentPanel.add(btnAddMore, "cell 2 0");
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }

        foo();
    }

    public void foo() {

        Field[] fields = Indices.class.getDeclaredFields();

        List<String> comboNames = new ArrayList<String>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(ShowOnTable.class)) {
                ShowOnTable[] annotationsByType = field.getAnnotationsByType(ShowOnTable.class);
                String name = annotationsByType[0].name();
                comboNames.add(name);
            }
        }
        Collections.sort(comboNames);

        for (String string : comboNames) {
            cboAttribute.addItem(string);
        }
    }
}
