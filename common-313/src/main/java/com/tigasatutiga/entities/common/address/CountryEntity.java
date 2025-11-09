package com.tigasatutiga.entities.common.address;

import com.tigasatutiga.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_ref_state")
public class CountryEntity extends BaseEntity {

    @Column(name = "iso")
    private String iso;

    @Column(name = "name")
    private String countryNameUppercase;

    @Column(name = "nicename")
    private String countryName;

    @Column(name = "iso3")
    private String iso3;

    @Column(name = "numbercode")
    private String numberCode;

    @Column(name = "phonecode")
    private String phoneCode;
}
