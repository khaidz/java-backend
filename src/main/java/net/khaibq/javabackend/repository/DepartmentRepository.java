package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.dto.department.DepartmentTreeProjection;
import net.khaibq.javabackend.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @EntityGraph(attributePaths = {"parent"})
    List<Department> findAll();

    @EntityGraph(attributePaths = {"parent"})
    Page<Department> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"parent"})
    Optional<Department> findById(Long id);

    @EntityGraph(attributePaths = {"parent"})
    Optional<Department> findByCode(String code);

    boolean existsByCode(String code);

    @Query(value = """
            WITH RECURSIVE DepartmentHierarchy AS (
                SELECT dept.id, dept.code, dept.name, dept.parent_code parentCode, dept.is_deleted isDeleted,\s
                       u.username managerUsername, 1 AS level
                FROM tbl_department dept
                LEFT JOIN tbl_user u ON dept.manager_username = u.username
                WHERE code = 'DED1E'
                UNION ALL
                SELECT d.id, d.code, d.name, d.parent_code parentCode, d.is_deleted isDeleted,
                       ur.username managerUsername, dh.level + 1 AS level
                FROM tbl_department d
                LEFT JOIN tbl_user ur ON d.manager_username = ur.username
                JOIN DepartmentHierarchy dh ON d.parent_code = dh.code
            )
            SELECT * FROM DepartmentHierarchy;
            """,
            nativeQuery = true)
    List<DepartmentTreeProjection> getDepartmentTree(String code);
}
