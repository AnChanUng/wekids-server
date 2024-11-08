package com.wekids.backend.child.repository;

import com.wekids.backend.member.domain.Child;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @EntityGraph(attributePaths = "account")
    List<Child> findAll();
}
