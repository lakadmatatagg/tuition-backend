package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InvoiceMapper extends BaseMapper<InvoiceEntity, InvoiceModel> {
    InvoiceMapper INVOICE_MAPPER = Mappers.getMapper(InvoiceMapper.class);

    List<InvoiceEntity> toEntityList(List<InvoiceModel> modelList);
    List<InvoiceModel> toModelList(List<InvoiceEntity> entityList);

    @Mapping(target = "id", source = "parent.id")
    @Mapping(target = "name", source = "parent.name")
    @Mapping(target = "phoneNo", source = "parent.phoneNo")
    @Mapping(target = "telegramChatId", source = "parent.telegramChatId")
    InvoiceTableModel toTableModel(ParentEntity parent, List<InvoiceEntity> invoices);

    default List<InvoiceTableModel> toTableModelList(List<InvoiceEntity> invoiceList, List<ParentEntity> parentList) {
        // Group invoices by parent ID
        Map<Long, List<InvoiceEntity>> invoicesByParentId = invoiceList.stream()
                .collect(Collectors.groupingBy(inv -> inv.getParent().getId()));

        // Map each parent to InvoiceTableModel
        return parentList.stream()
                .map(parent -> toTableModel(parent, invoicesByParentId.getOrDefault(parent.getId(), Collections.emptyList())))
                .toList();
    }
}