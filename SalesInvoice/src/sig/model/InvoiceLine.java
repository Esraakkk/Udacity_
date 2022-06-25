
package sig.model;

public class InvoiceLine {
    private InvoiceHeader invoice;
    private String name;
    private String itemName;
    private double price;
    private int count;
    private int invoiceNum;
    
    public InvoiceLine() {
        
    }

    

    public InvoiceLine(int invoiceNum, String itemName, double itemPrice, int count, InvoiceHeader inv) {
       this.invoiceNum = invoiceNum;
       this.itemName=itemName;
       this.price = itemPrice;
       this.count = count;
       this.invoice=inv;
    }

   
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLineTotal() {
        return price * count;
    }
    

    public String getAsCSV() {
        return invoice.getNum() + "," + itemName + "," + price + "," + count;
    }
    @Override
    public String toString() {
        return "InvoiceLine{" + "invoiceNum"+invoice.getNum()+"invoice=" + invoice + ", name=" + invoice.getCustomer() + ", itemName=" + itemName + ", price=" + price + ", count=" + count + '}';
    }

    public String getItemName() {
        return itemName;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }
    
    
    
    
}
