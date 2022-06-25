
package sig.model;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int num;
    private String customer;
    private String date;
    private ArrayList<InvoiceLine>lines;
   
    public double getInvoiceTotal()
    {
         double total = 0.0;
        for (InvoiceLine line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }

    public InvoiceHeader(int num, String customer, String date) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<InvoiceLine> getLines() {
        if(lines==null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num="  + num + ", customer=" + customer+ ", date=" + date+ '}';
    }
    
        public String getAsCSV() {
        return num + "," + date + "," + customer;
    }
}
