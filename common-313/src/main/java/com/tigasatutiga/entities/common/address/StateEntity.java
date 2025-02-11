package com.tigasatutiga.entities.common.address;

import com.tigasatutiga.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_ref_state")
public class StateEntity extends BaseEntity {

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "state_name")
    private String stateName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity country;

}
