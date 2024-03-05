package net.khaibq.javabackend.dto.department;

public interface DepartmentTreeProjection {
    Long getId();

    String getCode();

    String getName();

    Integer getLevel();

    String getParentCode();

    Integer getIsDeleted();

    String getManagerUsername();
}
