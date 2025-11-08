package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper extends BaseMapper<InvoiceItemEntity, InvoiceItemModel> {
    InvoiceItemMapper INVOICE_ITEM_MAPPER = Mappers.getMapper(InvoiceItemMapper.class);

    @Override
    @Mapping(target = "invoice", ignore = true) // prevent recursion
    InvoiceItemModel toModel(InvoiceItemEntity entity);

    @Override
    @Mapping(target = "invoice", ignore = true) // ignore again for reverse mapping
    InvoiceItemEntity toEntity(InvoiceItemModel model);

    List<InvoiceItemEntity> toEntityList(List<InvoiceItemModel> modelList);
    List<InvoiceItemModel> toModelList(List<InvoiceItemEntity> entityList);
}
