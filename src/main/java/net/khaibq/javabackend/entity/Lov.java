package net.khaibq.javabackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_lov")
@SequenceGenerator(name = "sequence_lov", sequenceName = "seq_lov", allocationSize = 1)
@Data
public class Lov {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_lov")
    private Long id;
    private String type;
    private String code;
    private String name;
}