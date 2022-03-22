package com.challenge.tecnico.xmen.mutant.detector.service;

import com.challenge.tecnico.xmen.mutant.detector.dto.MutantDto;
import com.challenge.tecnico.xmen.mutant.detector.dto.StatsDto;
import com.challenge.tecnico.xmen.mutant.detector.entity.MutantEntity;
import com.challenge.tecnico.xmen.mutant.detector.exception.AdnSequenceException;
import com.challenge.tecnico.xmen.mutant.detector.repository.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MutantServiceTest {

    private String[] adn = {"TTTTCA", "TATTTC", "CTACTT", "TCCAAG", "TCCCAA", "CCACTG"};

    //NO representa una matriz NxN
    private String[] adnError0 = {"TTTTC", "TATTTC", "CTACTT", "TCCAAG", "TCCCAA", "CCACTG"};

    //Matriz con valores no permitidos
    private String[] adnError1 = {"TTTTZT", "TATTTC", "CTACTT", "TCCAAG", "TCCCAA", "CCACTG"};

    private StatsDto statsDto;

    @Mock
    private MutantRepository mutantRepository;

    @InjectMocks
    private MutantService mutantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    void saveMutant() {
        MutantDto mutantDto = new MutantDto(adn, true);
        MutantEntity mutantEntity = new MutantEntity(adn, true);
        when(mutantRepository.save(any())).thenReturn(mutantEntity);
        assertTrue(mutantService.saveMutant(mutantDto));
    }

    @Test
    void getStats() {
        when(mutantRepository.countByMutant(true)).thenReturn(100);
        when(mutantRepository.countByMutant(false)).thenReturn(40);

        StatsDto statsDto = mutantService.getStats();
        assertEquals(100, statsDto.getCount_mutant_dna());
        assertEquals(40, statsDto.getCount_human_dna());
        assertEquals(2.5, statsDto.getRatio());
    }

    @Test
    void getMatrixAdn() {

        try {
            String[][] matrix = mutantService.getMatrixAdn(adn);
            assertNotNull(matrix);
            assertEquals(6, matrix.length);
        } catch (AdnSequenceException e) {
            fail();
        }

        try {
            String[][] matrix = mutantService.getMatrixAdn(adnError0);
            fail();
        } catch (AdnSequenceException e) {
            assertEquals("Invalid sequence, cannot be represented in a NxN table", e.getMessage());
        }

        try {
            String[][] matrix = mutantService.getMatrixAdn(adnError1);
            fail();
        } catch (AdnSequenceException e) {
            assertEquals("Invalid sequence, allowed values A,T,C,G", e.getMessage());
        }

    }

    @Test
    void findSequenceHorizontal() {
        try {
            // Cero secuencias
            adn = new String[]{"TTTACA", "TATTTC", "TTACTT", "TCCAAG", "TCCCGA", "CCACTG"};
            String[][] matrix = mutantService.getMatrixAdn(adn);
            int equalSequences = mutantService.findSequenceHorizontal(matrix, 0, 0, "", 1, 0);
            assertEquals(0, equalSequences);

            // 2 secuencias
            adn = new String[]{"TTTTCA", "TATTTC", "TTACTT", "TCCAAG", "TCCCCA", "CCACTG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findSequenceHorizontal(matrix, 0, 0, "", 1, 0);
            assertEquals(2, equalSequences);

            // 3 secuencias
            adn = new String[]{"TTTTCA", "TATTTC", "TTACTT", "GGGGAG", "TCCCCA", "CCACTG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findSequenceHorizontal(matrix, 0, 0, "", 1, 0);
            assertEquals(3, equalSequences);

        } catch (AdnSequenceException e) {
            fail();
        }
    }

    @Test
    void findSequenceVertical() {
        try {
            // Cero secuencias
            adn = new String[]{
                    "TTTACA",
                    "TATTTC",
                    "GTACTT",
                    "TCCAAG",
                    "TCCCGA",
                    "CCACTG"};
            String[][] matrix = mutantService.getMatrixAdn(adn);
            int equalSequences = mutantService.findSequenceVertical(matrix, 0, 0, "", 1, 0);
            assertEquals(0, equalSequences);

            // 2 secuencias
            adn = new String[]{
                    "TTTACA",
                    "TGTATC",
                    "GGAATT",
                    "TGCAAG",
                    "TGCCGA",
                    "CCACTG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findSequenceVertical(matrix, 0, 0, "", 1, 0);
            assertEquals(2, equalSequences);

            // 3 secuencias
            adn = new String[]{
                    "TTTACA",
                    "TGTATC",
                    "TGAATT",
                    "TGCAAG",
                    "TGCCGA",
                    "CCACTG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findSequenceVertical(matrix, 0, 0, "", 1, 0);
            assertEquals(3, equalSequences);

        } catch (AdnSequenceException e) {
            fail();
        }
    }

    @Test
    void findSequenceDiagonally() {
        try {
            // Cero secuencias
            adn = new String[]{
                    "TTTACA",
                    "TATTTC",
                    "GTACTT",
                    "TCCAAG",
                    "TCCCGA",
                    "ACACTG"};
            String[][] matrix = mutantService.getMatrixAdn(adn);
            int equalSequences = mutantService.findSequenceDiagonally(matrix, 0);
            assertEquals(0, equalSequences);

            // 2 secuencias
            adn = new String[]{
                    "TTTTCA",
                    "TGTACC",
                    "GTAATA",
                    "TGCAAT",
                    "TGCAGA",
                    "CCACCG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findSequenceDiagonally(matrix, 0);
            assertEquals(2, equalSequences);
        } catch (AdnSequenceException e) {
            fail();
        }
    }

    @Test
    void findReverseSequenceDiagonally() {
        try {
            // Cero secuencias
            adn = new String[]{
                    "TTTACA",
                    "TATTTC",
                    "GTACTT",
                    "TCCAAG",
                    "TCCCGA",
                    "ACACTG"};
            String[][] matrix = mutantService.getMatrixAdn(adn);
            int equalSequences = mutantService.findReverseSequenceDiagonally(matrix, 0);
            assertEquals(0, equalSequences);

            // 2 secuencias
            adn = new String[]{
                    "TTTTCA",
                    "TGTTCC",
                    "CTAATA",
                    "TCCAAT",
                    "TGCAGA",
                    "CCACCG"};
            matrix = mutantService.getMatrixAdn(adn);
            equalSequences = mutantService.findReverseSequenceDiagonally(matrix, 0);
            assertEquals(2, equalSequences);
        } catch (AdnSequenceException e) {
            fail();
        }
    }
}