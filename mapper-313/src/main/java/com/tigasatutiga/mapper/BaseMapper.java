package com.tigasatutiga.mapper;

import org.mapstruct.Mappings;

public interface BaseMapper<E, M>{
    @Mappings({})
    E toEntity(M model);

    @Mappings({})
    M toModel(E entity);
}
