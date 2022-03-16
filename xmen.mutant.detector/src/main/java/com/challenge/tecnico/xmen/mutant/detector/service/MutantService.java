package com.challenge.tecnico.xmen.mutant.detector.service;

import com.challenge.tecnico.xmen.mutant.detector.entity.MutantEntity;
import com.challenge.tecnico.xmen.mutant.detector.repository.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MutantService {

    @Autowired
    MutantRepository mutantRepository;

    public List<MutantEntity> list(){
        return mutantRepository.findAll();
    }

    //TODO Obtener Estadísticas de verificaciones de ADN
}
