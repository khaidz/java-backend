package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @EntityGraph(attributePaths = {"parent", "manager"})
    Page<Department> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"parent", "manager"})
    Optional<Department> findById(Long id);

    @EntityGraph(attributePaths = {"parent", "manager"})
    Optional<Department> findByCode(String code);

    @Query(value = """
                WITH RECURSIVE DepartmentHierarchy AS (
                SELECT dept.*, 1 AS level
                FROM tbl_department dept
                WHERE code = ?1
                UNION ALL
                SELECT d.*, dh.level + 1 AS level
                FROM tbl_department d
                JOIN DepartmentHierarchy dh ON d.parent_code = dh.code
            )
            SELECT * FROM DepartmentHierarchy;
            """, nativeQuery = true)
    List<Department> getDepartmentTree(String code);
}
