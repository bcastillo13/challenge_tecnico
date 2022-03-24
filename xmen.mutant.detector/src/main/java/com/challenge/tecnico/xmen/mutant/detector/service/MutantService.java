package com.challenge.tecnico.xmen.mutant.detector.service;

import com.challenge.tecnico.xmen.mutant.detector.dto.MutantDto;
import com.challenge.tecnico.xmen.mutant.detector.dto.StatsDto;
import com.challenge.tecnico.xmen.mutant.detector.entity.MutantEntity;
import com.challenge.tecnico.xmen.mutant.detector.exception.AdnSequenceException;
import com.challenge.tecnico.xmen.mutant.detector.repository.MutantRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MutantService {

    @Autowired
    MutantRepository mutantRepository;

    private static final Logger LOGGER = LogManager.getLogger(MutantService.class);

    public boolean saveMutant(MutantDto mutantDto){
        MutantEntity entity = new MutantEntity(mutantDto.getDna(), mutantDto.isMutant());
        return mutantRepository.save(entity).getId() > -1;
    }

    public StatsDto getStats(){
        float mutants = mutantRepository.countByMutant(true);
        float humans = mutantRepository.countByMutant(false);
        float ratio = 0;

        if(mutants > 0 && humans > 0){
            ratio = mutants/humans;
        }

        return new StatsDto((int) mutants, (int)humans, Math.round(ratio*100.0)/100.0);
    }

    /**
     * Serializa un array ADN a una matriz bidimensional.
     *
     * @param adn Array de Strings que representa cada base nitrogenada del ADN.
     * @return Matriz.
     * @throws AdnSequenceException en caso que la cadena de ADN sea inválida.
     */
    public String[][] getMatrixAdn(String[] adn) throws AdnSequenceException {
        int matrixSize = adn.length;
        String[][] matrix = new String[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            String[] row = adn[i].split("");
            if (row.length != matrixSize) {
                throw new AdnSequenceException("Invalid sequence, cannot be represented in a NxN table");
            }
            for (int j = 0; j < matrixSize; j++) {
                validateNitrogenBase(row[j]);
                matrix[i][j] = row[j];
            }
        }
        return matrix;
    }

    /**
     * Verifica que la secuencia de ADN ingresada tenga los tipos de base nitrogenada A,T,C,G.
     *
     * @param nitrogenBaseType representa el tipo de base nitrogenada.
     * @throws AdnSequenceException en caso de tener un caracter no esperado.
     */
    private void validateNitrogenBase(String nitrogenBaseType) throws AdnSequenceException {
        if (!nitrogenBaseType.equalsIgnoreCase("A")
                && !nitrogenBaseType.equalsIgnoreCase("T")
                && !nitrogenBaseType.equalsIgnoreCase("C")
                && !nitrogenBaseType.equalsIgnoreCase("G")) {
            throw new AdnSequenceException("Invalid sequence, allowed values A,T,C,G");
        }
    }

    /**
     * Busca secuencias de ADN de cuatro letras iguales en una matriz de manera horizontal.
     *
     * @param adn            Matriz con secuencia de ADN.
     * @param i              Posición en la matriz.
     * @param j              Posición en la matriz.
     * @param lastBase       Define la base anterior para compararla con la actual en el recorrido de la matriz.
     * @param count          contador de bases iguales en horiozontal.
     * @param equalSequences Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findSequenceHorizontal(String[][] adn, int i, int j, String lastBase, int count, int equalSequences) {
        lastBase = adn[i][j];
        int matrixSize = adn.length;
        if (i != matrixSize - 1 || j != matrixSize - 1) {
            j++;
            if (j < matrixSize && lastBase.equals(adn[i][j])) {
                count++;
                if (count == 4) {
                    count = 1;
                    equalSequences++;
                }
            } else {
                count = 1;
            }
            if (j == matrixSize) {
                j = 0;
                lastBase = "";
                count = 1;
                i++;
            }
            equalSequences = findSequenceHorizontal(adn, i, j, lastBase, count, equalSequences);
        }
        return equalSequences;
    }

    /**
     * Busca secuencias de ADN de cuatro letras iguales en una matriz de manera vertical.
     *
     * @param adn            Matriz con secuencia de ADN.
     * @param i              Posición en la matriz.
     * @param j              Posición en la matriz.
     * @param lastBase       Define la base anterior para compararla con la actual en el recorrido de la matriz.
     * @param count          contador de bases iguales en vertical.
     * @param equalSequences Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findSequenceVertical(String[][] adn, int i, int j, String lastBase, int count, int equalSequences) {
        lastBase = adn[i][j];
        int matrixSize = adn.length;
        if (i != matrixSize - 1 || j != matrixSize - 1) {
            i++;
            if (i < matrixSize && lastBase.equals(adn[i][j])) {
                count++;
                if (count == 4) {
                    count = 1;
                    equalSequences++;
                }
            } else {
                count = 1;
            }
            if (i == matrixSize) {
                i = 0;
                count = 1;
                j++;
            }
            equalSequences = findSequenceVertical(adn, i, j, lastBase, count, equalSequences);
        }
        return equalSequences;
    }

    /**
     * Busca secuencias de ADN de cuatro letras iguales en una matriz de manera diagonal progresivamente.
     *
     * @param adn            Matriz con secuencia de ADN.
     * @param equalSequences Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findSequenceDiagonally(String[][] adn, int equalSequences) {
        int matrixSize = adn.length;
        for (int diagonal = 1 - matrixSize; diagonal <= matrixSize - 1; diagonal++) {
            // Diagonal actual
            int actDiagonal = Math.max(0, diagonal);
            String lastBase = adn[0][actDiagonal];
            int count = 0;

            for (int horizontal = actDiagonal, vertical = Math.min(matrixSize - 1, matrixSize - 1 + diagonal); horizontal <= matrixSize - 1 + diagonal && horizontal <= matrixSize - 1; vertical--, horizontal++) {

                if (lastBase.equals(adn[vertical][horizontal])) {
                    count++;
                    if (count == 4) {
                        count = 0;
                        equalSequences++;
                    }
                } else {
                    lastBase = adn[vertical][horizontal];
                    count = 1;
                }

            }

        }
        return equalSequences;
    }

    /**
     * Busca secuencias de ADN de cuatro letras iguales en una matriz de manera diagonal regresivamente.
     *
     * @param adn            Matriz con secuencia de ADN.
     * @param equalSequences Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findReverseSequenceDiagonally(String[][] adn, int equalSequences) {
        int matrixSize = adn.length;
        for (int diagonal = 1 - matrixSize; diagonal <= matrixSize - 1; diagonal++) {
            // Diagonal actual
            int actDiagonal = Math.max(0, diagonal);
            String lastBase = adn[0][actDiagonal];
            int count = 0;

            for (int vertical = actDiagonal, horizontal = -Math.min(0, diagonal); vertical < matrixSize && horizontal < matrixSize; vertical++, horizontal++) {

                if (lastBase.equals(adn[vertical][horizontal])) {
                    count++;
                    if (count == 4) {
                        count = 0;
                        equalSequences++;
                    }
                } else {
                    lastBase = adn[vertical][horizontal];
                    count = 1;
                }

            }
        }
        return equalSequences;
    }


}
