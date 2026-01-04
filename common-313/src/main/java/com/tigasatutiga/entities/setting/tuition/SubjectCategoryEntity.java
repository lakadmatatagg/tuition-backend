package com.tigasatutiga.entities.setting.tuition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REF_SUBJECT_CATEGORY")
public class SubjectCategoryEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CODE")
    private String categoryCode;

    @Column(name = "NAME")
    private String categoryName;

    @Column(name = "PRICE")
    private BigDecimal price;
}
