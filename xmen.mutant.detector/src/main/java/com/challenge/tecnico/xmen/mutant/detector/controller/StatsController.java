package com.challenge.tecnico.xmen.mutant.detector.controller;

import com.challenge.tecnico.xmen.mutant.detector.dto.StatsDto;
import com.challenge.tecnico.xmen.mutant.detector.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    MutantService mutantService;

    /**
     * Obtiene las el conteo de humanos y mutantes y su ratio
     * @return Stats
     */
    @GetMapping("")
    public StatsDto getStats(){
       return mutantService.getStats();
    }

}
