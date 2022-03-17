package com.challenge.tecnico.xmen.mutant.detector.controller;

import com.challenge.tecnico.xmen.mutant.detector.dto.Message;
import com.challenge.tecnico.xmen.mutant.detector.exception.AdnSequenceException;
import com.challenge.tecnico.xmen.mutant.detector.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    @Autowired
    MutantService mutantService;

    /**
     * Determina si un humano es mutante o no basado en el análisis de su secuencia de ADN
     *
     * @param adn Array de Strings que representa cada base nitrogenada del ADN.
     */
    @PostMapping("")
    public ResponseEntity<?> isMutant(@RequestBody String[] adn) {
        ResponseEntity responseEntity;
        try {
            int equalSequences = 0;
            String[][] matrix = mutantService.getMatrixAdn(adn);

            //Búsqueda horizontak
            equalSequences = mutantService.findSequenceHorizontal(matrix, 0, 0, ",", 1, 0);

            //Búsqueda vertical
            equalSequences = mutantService.findSequenceVertical(matrix, 0, 0, ",", 1, 0) + equalSequences;

            //TODO integrar busqueda de secuencias oblicuas (en desarrollo en stand alone)

            //TODO Borrar esto, solo es un ejemplo para mostrar la matriz ingresada
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }

            if (equalSequences >= 2) {
                return new ResponseEntity<>(new Message("Es mutante"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Message("Es humano"), HttpStatus.FORBIDDEN);
            }

            //TODO guardar registros en BBDD
            //TODO integrar unitTest
            //TODO integrar logs



        } catch (AdnSequenceException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
