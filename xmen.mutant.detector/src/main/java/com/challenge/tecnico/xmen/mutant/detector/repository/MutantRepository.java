package com.challenge.tecnico.xmen.mutant.detector.repository;

import com.challenge.tecnico.xmen.mutant.detector.entity.MutantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends JpaRepository<MutantEntity, Integer> {

}
