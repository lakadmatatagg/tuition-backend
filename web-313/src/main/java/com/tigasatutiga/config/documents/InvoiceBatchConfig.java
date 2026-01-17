package com.tigasatutiga.config.documents;

import com.tigasatutiga.config.documents.collector.InvoiceCollector;
import com.tigasatutiga.config.documents.listener.InvoiceJobListener;
import com.tigasatutiga.exception.BusinessException;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.service.documents.InvoiceSO;
import com.tigasatutiga.service.reference.ReferenceCodeSO;
import com.tigasatutiga.service.setting.SystemSettingSO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class InvoiceBatchConfig {

    private static final int CHUNK_SIZE = 10;

    @Autowired
    private InvoiceSO invoiceSO;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ReferenceCodeSO referenceCodeSO;

    @Autowired
    private SystemSettingSO systemSettingSO;

    @Autowired
    private InvoiceCollector collector;

    @Autowired
    private InvoiceJobListener listener;

    /* -------------------- Job Repository -------------------- */
    @Bean
    public JobRepository jobRepository(
            DataSource dataSource,
            PlatformTransactionManager transactionManager
    ) throws Exception {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setDatabaseType("mysql");
        factory.setTablePrefix("BATCH_");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /* -------------------- Reader -------------------- */
    @Bean
    public ItemReader<Long> parentReader() {
        return new ItemReader<>() {

            private final List<Long> parentIds =
                    parentRepository.findParentIdsWithoutInvoice(
                            LocalDate.now().withDayOfMonth(1)
                    );

            private int index = 0;

            @Override
            public Long read() {
                if (index < parentIds.size()) {
                    return parentIds.get(index++);
                }
                return null;
            }
        };
    }

    /* -------------------- Processor -------------------- */
    @Bean
    public ItemProcessor<Long, InvoiceTemplateModel> invoiceProcessor() {
        return parentId -> {

            BigDecimal annualFee =
                    new BigDecimal(systemSettingSO.getByKey("annual_fee").getValue());

            String annualDesc =
                    systemSettingSO.getByKey("annual_fee_description").getValue();

            InvoiceModel invoiceModel =
                    invoiceSO.buildInvoiceModel(parentId, annualFee, annualDesc);

            InvoiceModel savedInvoice =
                    invoiceSO.createInvoiceWithItems(invoiceModel);

            List<ReferenceCodeModel> companyInfo =
                    referenceCodeSO.getListByGroup("COMPANY_INFO");

            List<ReferenceCodeModel> invoiceLabels =
                    referenceCodeSO.getListByGroup("INVOICE_LABELS");

            return invoiceSO.buildInvoiceTemplate(
                    savedInvoice,
                    companyInfo,
                    invoiceLabels
            );
        };
    }

    /* -------------------- Writer (COLLECT ONLY) -------------------- */
    @Bean
    public ItemWriter<InvoiceTemplateModel> invoiceWriter() {
        return items -> {
            List<InvoiceTemplateModel> batch = new ArrayList<>();
            items.forEach(batch::add);
            collector.addAll(batch);
        };
    }

    /* -------------------- Step -------------------- */
    @Bean
    public Step generateInvoicesStep(
            JobRepository jobRepository,
            PlatformTransactionManager tx
    ) {

        return new StepBuilder("generateInvoicesStep", jobRepository)
                .<Long, InvoiceTemplateModel>chunk(CHUNK_SIZE, tx)
                .reader(parentReader())
                .processor(invoiceProcessor())
                .writer(invoiceWriter())
                .faultTolerant()
                .skip(BusinessException.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();
    }

    /* -------------------- Job -------------------- */
    @Bean
    public Job generateMonthlyInvoicesJob(
            JobRepository jobRepository,
            PlatformTransactionManager tx
    ) {

        return new org.springframework.batch.core.job.builder.JobBuilder(
                "generateMonthlyInvoicesJob",
                jobRepository
        )
                .listener(listener) // sends ONE email at job end
                .start(generateInvoicesStep(jobRepository, tx))
                .build();
    }
}
