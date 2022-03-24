package com.challenge.tecnico.xmen.mutant.detector.dto;

public class MutantDto {

    private String[] adnSequence;

    private boolean mutant;

    public MutantDto() {
        //Empty
    }

    public MutantDto(String[] adnSequence, boolean mutant) {
        this.adnSequence = adnSequence;
        this.mutant = mutant;
    }

    public String[] getAdnSequence() {
        return adnSequence;
    }

    public boolean isMutant() {
        return mutant;
    }

}
