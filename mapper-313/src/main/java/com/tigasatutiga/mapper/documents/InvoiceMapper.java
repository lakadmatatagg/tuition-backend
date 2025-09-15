package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.InvoiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper extends BaseMapper<InvoiceEntity, InvoiceModel> {
    InvoiceMapper INVOICE_MAPPER = Mappers.getMapper(InvoiceMapper.class);

    List<InvoiceEntity> toEntityList(List<InvoiceModel> modelList);
    List<InvoiceModel> toModelList(List<InvoiceEntity> entityList);
}