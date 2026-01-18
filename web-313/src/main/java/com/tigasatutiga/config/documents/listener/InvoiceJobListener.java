package com.tigasatutiga.config.documents.listener;

import com.tigasatutiga.config.documents.collector.InvoiceCollector;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class InvoiceJobListener implements JobExecutionListener {

    @Autowired
    private InvoiceCollector collector;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservices.filer.url}")
    private String filerURL;

    @Value("${microservices.filer.batch-api}")
    private String filerBatchApi;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        collector.clear(); // clean previous run
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        List<InvoiceTemplateModel> allInvoices = collector.getAllInvoices();
        if (!allInvoices.isEmpty()) {
            String url = filerURL + filerBatchApi;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<InvoiceTemplateModel>> entity = new HttpEntity<>(allInvoices, headers);

            restTemplate.postForEntity(url, entity, BatchInvoiceResponseModel.class);

            collector.clear(); // optional cleanup
        }
    }
}