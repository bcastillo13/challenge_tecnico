package com.challenge.tecnico.xmen.mutant.detector.controller;

import com.challenge.tecnico.xmen.mutant.detector.dto.MutantDto;
import com.challenge.tecnico.xmen.mutant.detector.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    MutantService mutantService;

    @PostMapping("")
    public void analizeADN(MutantDto mutantDto){

    }

}
