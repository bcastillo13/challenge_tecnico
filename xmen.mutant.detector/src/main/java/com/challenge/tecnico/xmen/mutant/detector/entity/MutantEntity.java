package com.challenge.tecnico.xmen.mutant.detector.entity;

import javax.persistence.*;

@Entity
@Table(name = "mutant")
public class MutantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String adnSecuence ;

    private boolean isMutant;

    public MutantEntity() {
        //Empty
    }

    public MutantEntity(String adnSecuence, boolean isMutant) {
        this.adnSecuence = adnSecuence;
        this.isMutant = isMutant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdnSecuence() {
        return adnSecuence;
    }

    public void setAdnSecuence(String adnSecuence) {
        this.adnSecuence = adnSecuence;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }
}
