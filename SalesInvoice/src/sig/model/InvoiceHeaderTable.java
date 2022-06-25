
package sig.model;


import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import javax.swing.table.AbstractTableModel;

public class InvoiceHeaderTable extends AbstractTableModel {
    private ArrayList<InvoiceHeader> invoices;
    private String[] columns = {"No.", "Date", "Customer", "Total"};
    
    
    
    public InvoiceHeaderTable(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public InvoiceHeaderTable() {
        
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void setInvoices(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getNum();
            case 2: return invoice.getDate();
            case 1: return invoice.getCustomer();
            case 3: return invoice.getInvoiceTotal();
            default : return "";
        }
        
        
    }

  

    public ArrayList<InvoiceHeader> getInvoices() {
        return invoices;
    }

    public String[] getColumns() {
        return columns;
    }

 


}
