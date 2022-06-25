
package sig.controller;

import sig.model.InvoiceHeader;
import sig.model.InvoiceHeaderTable;
import sig.model.InvoiceLine;
import sig.view.InvoiceFrame;
import sig.model.InvoiceLineTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sig.view.InvDialog;
import sig.view.LineDialog;


public class ActionHandler implements ActionListener , ListSelectionListener{

    private InvoiceFrame frame;
    public ActionHandler (InvoiceFrame frame){
        this.frame=frame;
    }
    private InvDialog invDialog;
    private LineDialog lineDialog;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Handling called!");
        
        switch(e.getActionCommand())
        {
            case "New Invoice":
                System.out.println("New Invoice"); 
                newInv();
                break;
            case "Delete Invoice":
                System.out.println("Delete Invoice");
                deleteInv();
                break;
            case "New Item":
                System.out.println("New Item");
                newItem();
                break;
            case "Delete Item":
                System.out.println("Delete Item");
                deleteItem();
                break;
            case "Load File":
                System.out.println("Load File");
                loadFile();
                break;
            case "Save File":
                System.out.println("Save File");
                saveFile();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    
    private void newInv() {
        invDialog= new InvDialog(frame);
        invDialog.setVisible(true);
    }

    private void deleteInv() {
     int selectedRow=   frame.getHeaderTable().getSelectedRow();
      if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoiceHeaderTable().fireTableDataChanged();
        }
    }

    private void newItem() {
    lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
           int selectedInv =frame.getHeaderTable().getSelectedRow();
           int selectedRow = frame.getLineTable().getSelectedRow();
      if (selectedInv!= -1 && selectedRow != -1) {
          InvoiceHeader invoice=frame.getInvoices().get(selectedInv);
          invoice.getLines().remove(selectedRow);
          InvoiceLineTable LinesTableModel= new InvoiceLineTable(invoice.getLines());
            frame.getLineTable().setModel(LinesTableModel);
            LinesTableModel.fireTableDataChanged();
        }
    }

    private void saveFile() {
          ArrayList<InvoiceHeader> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (InvoiceLine line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }
    
    

    @Override
    public void valueChanged(ListSelectionEvent e) {
      int selectedIndex = frame.getHeaderTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceHeader currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getNumLable().setText("" + currentInvoice.getNum());
            frame.getDateLable().setText(currentInvoice.getDate());
            frame.getCustomerLable().setText(currentInvoice.getCustomer());
            frame.getTotalLable().setText("" + currentInvoice.getInvoiceTotal());
           InvoiceLineTable linesTableModel = new InvoiceLineTable(currentInvoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    
    private void loadFile()  {
        JFileChooser fc=new JFileChooser();
        
        try{
        int result=fc.showOpenDialog(frame);
        if(result==JFileChooser.APPROVE_OPTION){
           File headerFile=fc.getSelectedFile();
           Path headerPath=Paths.get(headerFile.getAbsolutePath());
           List<String> headerLines= Files.readAllLines(headerPath);
            System.out.println("Invoices have been read");
            ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
             for (String headerLine : headerLines) {
                    String[] headerParts = headerLine.split(",");
                    int invoiceNum = Integer.parseInt(headerParts[0]);
                    String invoiceDate = headerParts[1];
                    String customerName = headerParts[2];
                    
                    InvoiceHeader invoice = new InvoiceHeader(invoiceNum, customerName,invoiceDate);
                    invoicesArray.add(invoice);
                }
             System.out.println("Check point");
             result = fc.showOpenDialog(frame);
             
                   if(result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Lines have been read");
                    
                        for (String lineLine : lineLines) {
                        String lineParts[] = lineLine.split(",");
                        int invoiceNum = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemPrice = Double.parseDouble(lineParts[2]);
                        int count = Integer.parseInt(lineParts[3]);
                        InvoiceHeader inv = null;
                        for (InvoiceHeader invoice : invoicesArray) {
                            if (invoice.getNum() == invoiceNum) {
                                inv = invoice;
                                break;
                            }
                        }
                        InvoiceLine line = new InvoiceLine(invoiceNum, itemName, itemPrice, count, inv);
                        inv.getLines().add(line);
                        }
                         System.out.println("Check point");
        } 
            frame.setInvoices(invoicesArray);
           InvoiceHeaderTable invoicesHeaderTable = new InvoiceHeaderTable(invoicesArray);
            frame.setInvoiceHeaderTable(invoicesHeaderTable);
            frame.getHeaderTable().setModel(invoicesHeaderTable);
            frame.getInvoiceHeaderTable().fireTableDataChanged();
        }
        }catch(IOException ex){
        ex.printStackTrace();
        }
        }

    private void createInvoiceCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    
        private void createInvoiceOK() {
      
        String date = invDialog.getInvDateField().getText();
        String customer = invDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();
       
        try {
            String[] dateParts = date.split("-"); 
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                   InvoiceHeader invoice = new InvoiceHeader(num, customer,date);
                    frame.getInvoices().add(invoice);
                    frame.getInvoiceHeaderTable().fireTableDataChanged();
                    
                    invDialog.setVisible(false);
                    invDialog.dispose();
                    invDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getHeaderTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeader invoice = frame.getInvoices().get(selectedInvoice);
            InvoiceLine line = new InvoiceLine(invoice.getNum(),item, price, count, invoice);
            invoice.getLines().add(line);
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }
    
    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

 
}
    

    
    

