package com.challenge.tecnico.xmen.mutant.detector.controller;

import com.challenge.tecnico.xmen.mutant.detector.dto.Message;
import com.challenge.tecnico.xmen.mutant.detector.dto.MutantDto;
import com.challenge.tecnico.xmen.mutant.detector.exception.AdnSequenceException;
import com.challenge.tecnico.xmen.mutant.detector.service.MutantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(MutantController.class);

    /**
     * Determina si un humano es mutante o no basado en el análisis de su secuencia de ADN
     *
     * @param adn Array de Strings que representa cada base nitrogenada del ADN.
     */
    @PostMapping("")
    public ResponseEntity<?> isMutant(@RequestBody String[] adn) {

        try {
            boolean isMutant = false;
            long tsIni = System.currentTimeMillis();
            int equalSequences = 0;
            String[][] matrix = mutantService.getMatrixAdn(adn);

            //Búsqueda horizontal
            equalSequences = mutantService.findSequenceHorizontal(matrix, 0, 0, "", 1, equalSequences);

            //Búsqueda vertical
            equalSequences = mutantService.findSequenceVertical(matrix, 0, 0, "", 1, equalSequences);

            //Búsqueda diagonal hacia atrás
            equalSequences = mutantService.findReverseSequenceDiagonally(matrix, equalSequences);

            //Búsqueda diagonal hacia adelante
            equalSequences = mutantService.findSequenceDiagonally(matrix, equalSequences);

            if (equalSequences >= 2) isMutant = true;
            MutantDto mutantDto = new MutantDto(adn, isMutant);
            mutantService.saveMutant(mutantDto);

            LOGGER.info("Total de secuencias encontradas = {}", equalSequences);
            LOGGER.info("Tiempo de análisis de la cadena = {} milisegundos", (System.currentTimeMillis() - tsIni));

            if (isMutant) {
                return new ResponseEntity<>(new Message("Es mutante"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Message("Es humano"), HttpStatus.FORBIDDEN);
            }

        } catch (AdnSequenceException e) {
            return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
