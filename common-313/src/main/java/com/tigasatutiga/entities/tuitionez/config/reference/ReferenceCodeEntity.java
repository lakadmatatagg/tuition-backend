package com.tigasatutiga.entities.tuitionez.config.reference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Accessors
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REF_CODE_STRUCT")
public class ReferenceCodeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REF_GROUP_ID", referencedColumnName = "ID")
    private ReferenceGroupEntity group;

    @Column(name = "REF_CODE")
    private String code;

    @Column(name = "REF_LABEL")
    private String name;

    @Column(name = "REF_ORDER")
    private int order;

    @Column(name = "ACTIVE")
    private boolean active;
}
