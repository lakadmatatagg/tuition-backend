package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper extends BaseMapper<InvoiceItemEntity, InvoiceItemModel> {
    InvoiceItemMapper INVOICE_ITEM_MAPPER = Mappers.getMapper(InvoiceItemMapper.class);

    List<InvoiceItemEntity> toEntityList(List<InvoiceItemModel> modelList);
    List<InvoiceItemModel> toModelList(List<InvoiceItemEntity> entityList);
}
