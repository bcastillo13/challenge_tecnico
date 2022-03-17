package com.challenge.tecnico.xmen.mutant.detector.entity;

import javax.persistence.*;

@Entity
@Table(name = "mutant")
public class MutantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String adnSequence ;

    private boolean isMutant;

    public MutantEntity() {
        //Empty
    }

    public MutantEntity(String adnSequence, boolean isMutant) {
        this.adnSequence = adnSequence;
        this.isMutant = isMutant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getadnSequence() {
        return adnSequence;
    }

    public void setadnSequence(String adnSequence) {
        this.adnSequence = adnSequence;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }
}
