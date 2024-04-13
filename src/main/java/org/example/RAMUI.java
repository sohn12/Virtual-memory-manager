package org.example;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class RAMUI extends JFrame {
    private int[] dataArray;
    private final MyTableModel model;

    public RAMUI(int[] initialData) {
        dataArray = initialData;
        model = new MyTableModel();

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);

        setTitle("RAM UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);

        setSize(650, 650);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public void updateData(int[] updatedData) {
        model.updateData(updatedData);
    }

    private class MyTableModel extends AbstractTableModel {
        private static final int NUM_COLUMNS = 8; // Number of columns

        @Override
        public int getRowCount() {
            return (int) Math.ceil((double) dataArray.length / NUM_COLUMNS);
        }

        @Override
        public int getColumnCount() {
            return NUM_COLUMNS;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int index = rowIndex * NUM_COLUMNS + columnIndex;
            if (index < dataArray.length) {
                return dataArray[index];
            } else {
                return null;
            }
        }
        public void updateData(int[] updatedData) {
            dataArray = updatedData;
            model.fireTableDataChanged();
        }
    }

}
