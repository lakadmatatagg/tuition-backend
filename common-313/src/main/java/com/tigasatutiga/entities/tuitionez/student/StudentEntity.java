package com.tigasatutiga.entities.tuitionez.student;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRX_STUDENT")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MYKID_NO")
    private String mykid;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "JUNC_STUDENT_GRADE",
            joinColumns = { @JoinColumn(name = "STUDENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "GRADE_ID") }
    )
    private List<SubjectCategoryEntity> grades;

    @ManyToMany
    @JoinTable(
            name = "JUNC_STUDENT_SUBJECT",
            joinColumns = { @JoinColumn(name = "STUDENT_ID") },
            inverseJoinColumns = { @JoinColumn(name = "SUBJECT_ID") }
    )
    private List<SubjectEntity> subjects;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID")
    private ParentEntity parent;
}
