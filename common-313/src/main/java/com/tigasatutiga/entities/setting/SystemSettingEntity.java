package com.tigasatutiga.entities.setting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REF_SYSTEM_SETTING")
public class SystemSettingEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SETTING_KEY")
    private String key;

    @Column(name = "SETTING_VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

}
