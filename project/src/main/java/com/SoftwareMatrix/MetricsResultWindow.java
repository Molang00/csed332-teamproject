package com.SoftwareMatrix;

import com.google.common.collect.Tables;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.awt.image.BufferedImage;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    JTable table, MITable, OOTable;
    JScrollPane tableContent;
    Integer MIscore, OOscore;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        String header[] = { "Type", "Score", "Graph" };
        String body[][] = { { "MI", "", "" }, { "OO", "", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable();
        table.setModel(model);
        tableContent = new JScrollPane(table);
        System.out.println("make table");

        settingAllStatus();
        table.setValueAt(MIscore, 0, 1);
        table.setValueAt(OOscore, 1, 1);
        table.getColumn("Type").setCellRenderer(new ButtonRenderer());
        table.getColumn("Type").setCellEditor(new ButtonEditor(new JCheckBox(), this));
        table.getColumn("Graph").setCellRenderer(new ColoredTableCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        changeView("MI");

    }

    public Integer getOOsocre() {
        return OOscore;
    }

    public Integer getMIscore() {
        return MIscore;
    }

    public void setOOscore(Integer s) {
        OOscore = s;
    }

    public void setMIscore(Integer s) {
        MIscore = s;
    }

    public void settingAllStatus() {
        setMIscore(90);
        setOOscore(10);
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JScrollPane getContent() {
        return tableContent;
    }

    public void changeView(String label){
        System.out.println(label);
        if(MITable == null){
            System.out.println("start");
            generateMITable();
            System.out.println("end");
        }
    }

    public void generateMITable(){
        MITable = new JTable();
        String header[] = { "MI Features", "Score", "Graph" };
        String body[][] = { { "V (Haslstead's volume)", "", "" }, { "G Cyclomatic complexity)", "", "" }, { "LOC(Source lines of code", "", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        MITable.setModel(model);
        tableContent = new JScrollPane(MITable);
        MITable.setValueAt(150, 0, 1); // Set V value
        MITable.setValueAt(20, 1, 1); // Set G value
        MITable.setValueAt(300, 2, 1); // Set LOC value
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
    }

    class ColoredTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
                int row, int column) {
            setEnabled(table == null || table.isEnabled()); // see question above
            int r, g, b = 0;

            if (row == 0) {
                r = (int) ((100 - MIscore) * 2.55);
                g = (int) ((MIscore) * 2.55);
                Color c = new Color(r, g, b);
                setBackground(c);
            } else {
                r = (int) ((100 - OOscore) * 2.55);
                g = (int) ((OOscore) * 2.55);
                Color c = new Color(r, g, b);
                setBackground(c);
            }

            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            return this;
        }
    }

}
