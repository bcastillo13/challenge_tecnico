package com.challenge.tecnico.xmen.mutant.detector.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
@Entity
@Table(name = "MUTANT_ID")
public class MutantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Type(type = "string-array")
    @Column(name = "ADN_SEQUENCE", columnDefinition = "text[]")
    private String[] adnSequence;

    @Column(name = "MUTANT")
    private boolean mutant;

    public MutantEntity() {
        //Empty
    }

    public MutantEntity(String[] adnSequence, boolean mutant) {
        this.adnSequence = adnSequence;
        this.mutant = mutant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getadnSequence() {
        return adnSequence;
    }

    public void setadnSequence(String[] adnSequence) {
        this.adnSequence = adnSequence;
    }

    public boolean isMutant() {
        return mutant;
    }

    public void setMutant(boolean mutant) {
        mutant = mutant;
    }
}
