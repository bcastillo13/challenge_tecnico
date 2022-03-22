package com.challenge.tecnico.xmen.mutant.detector.controller;

import com.challenge.tecnico.xmen.mutant.detector.service.MutantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MutantControllerTest {

    @InjectMocks
    private MutantController mutantController;

    @Mock
    private MutantService mutantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //TODO Completar TEST
    @Test
    public void isMutant() {
        String[]  adn = new String[]{"TTTTCA", "TATTTC", "TCACTT", "TCCAAG", "TCCCCA", "CCACTG"};
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mutantService.saveMutant(any())).thenReturn(true);
        ResponseEntity<?> responseEntity = mutantController.isMutant(adn);
        assertEquals(403, responseEntity.getStatusCodeValue());
    }

}