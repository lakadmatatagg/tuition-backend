package com.tigasatutiga.entities.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REF_RUNNING_NO",
        uniqueConstraints = @UniqueConstraint(name = "REF_RUNNING_NO_UNIQUE", columnNames = {"DOC_CODE", "PREFIX", "SUFFIX"}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class RunningNoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DOC_CODE", nullable = false, length = 50)
    private String docCode;

    @Column(name = "PREFIX", nullable = false, length = 50)
    private String prefix;

    @Column(name = "RUNNING_NO", nullable = false)
    private String runningNo = "000001"; // now VARCHAR, default padded

    @Column(name = "SUFFIX", length = 50)
    private String suffix;
}
