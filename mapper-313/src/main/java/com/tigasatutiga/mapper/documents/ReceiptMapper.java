package com.tigasatutiga.mapper.documents;

import com.tigasatutiga.entities.documents.ReceiptEntity;
import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.models.documents.ReceiptModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceiptMapper extends BaseMapper<ReceiptEntity, ReceiptModel> {
    ReceiptMapper RECEIPT_MAPPER = Mappers.getMapper(ReceiptMapper.class);

    List<ReceiptEntity> toEntityList(List<ReceiptModel> modelList);
    List<ReceiptModel> toModelList(List<ReceiptEntity> entityList);
}
