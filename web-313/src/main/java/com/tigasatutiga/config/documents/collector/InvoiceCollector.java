package com.tigasatutiga.config.documents.collector;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceCollector {

    private final List<InvoiceTemplateModel> allInvoices = new ArrayList<>();

    // thread-safe addition
    public synchronized void addAll(List<InvoiceTemplateModel> invoices) {
        allInvoices.addAll(invoices);
    }

    public synchronized List<InvoiceTemplateModel> getAllInvoices() {
        return new ArrayList<>(allInvoices);
    }

    public synchronized void clear() {
        allInvoices.clear();
    }
}