package com.challenge.tecnico.xmen.mutant.detector.service;

import com.challenge.tecnico.xmen.mutant.detector.entity.MutantEntity;
import com.challenge.tecnico.xmen.mutant.detector.exception.AdnSequenceException;
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

    public List<MutantEntity> list() {
        return mutantRepository.findAll();
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
     * @param nitrogeBaseType representa el tipo de base nitrogenada.
     * @throws AdnSequenceException en caso de tener un caracter no esperado.
     */
    private void validateNitrogenBase(String nitrogeBaseType) throws AdnSequenceException {
        if (!nitrogeBaseType.equalsIgnoreCase("A")
                && !nitrogeBaseType.equalsIgnoreCase("T")
                && !nitrogeBaseType.equalsIgnoreCase("C")
                && !nitrogeBaseType.equalsIgnoreCase("G")) {
            throw new AdnSequenceException("Invalid sequence, allowed values A,T,C,G");
        }
    }

    /**
     * Busca secuencias de ADN de cuatro letras iguales en una matriz de manera horizontal.
     * @param adn Matriz con secuencia de ADN.
     * @param i Posición en la matriz.
     * @param j Posición en la matriz.
     * @param lastBase Define la base anterior para compararla con la actual en el recorrido de la matriz.
     * @param count contador de bases iguales en horiozontal.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findSequenceHorizontal(String [][] adn, int i, int j, String lastBase, int count, int equalSequences) {
        lastBase = adn[i][j];
        int matrixSize = adn.length;
        if (i != matrixSize - 1 || j != matrixSize - 1) {
            j++;

            if(j < matrixSize && lastBase.equals(adn[i][j])){
                count ++;
                if(count == 4) {
                    count = 0;
                    equalSequences++;
                }
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
     * @param adn Matriz con secuencia de ADN.
     * @param i Posición en la matriz.
     * @param j Posición en la matriz.
     * @param lastBase Define la base anterior para compararla con la actual en el recorrido de la matriz.
     * @param count contador de bases iguales en vertical.
     * @return Número de secuencias de 4 letras iguales consecutivas que se encontraronal recorrer de manera horizontal la matriz.
     */
    public int findSequenceVertical(String [][] adn, int i, int j, String lastBase, int count, int equalSequences) {
        lastBase = adn[i][j];
        int matrixSize = adn.length;
        if (i != matrixSize - 1 || j != matrixSize - 1) {
            i++;

            if(j < matrixSize && lastBase.equals(adn[i][j])){
                count ++;
                if(count == 4) {
                    count = 0;
                    equalSequences++;
                }
            }
            if (j == matrixSize) {
                j = 0;
                lastBase = "";
                count = 1;
                j++;
            }
            equalSequences = findSequenceHorizontal(adn, i, j, lastBase, count, equalSequences);
        }
        return equalSequences;
    }
}
