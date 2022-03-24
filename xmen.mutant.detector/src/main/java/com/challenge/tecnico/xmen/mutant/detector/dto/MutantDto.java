package com.challenge.tecnico.xmen.mutant.detector.dto;

public class MutantDto {

    private String[] dna;

    private boolean mutant;

    public MutantDto() {
        //Empty
    }

    public MutantDto(String[] dna, boolean mutant) {
        this.dna = dna;
        this.mutant = mutant;
    }

    public String[] getDna() {
        return dna;
    }

    public boolean isMutant() {
        return mutant;
    }

    public void setMutant(boolean mutant) {
        this.mutant = mutant;
    }
}
