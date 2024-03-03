package net.khaibq.javabackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "tbl_department")
@Data
public class Department extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_code", referencedColumnName = "code")
    private Department parent;

    @ManyToOne
    @JoinColumn(name = "manager_username", referencedColumnName = "username")
    @JsonBackReference
    private User manager;
}
