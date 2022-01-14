package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "accident_coef")
public class AccidentCoef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accident_coef_id")
    @NotNull
    private int accidentCoefId;

    @Column(name = "accident_coef")
    @NotNull
    @Positive(message = "Accident Coefficient must be positive!")
    private BigDecimal accidentCoef;

    public AccidentCoef() {

    }

    public AccidentCoef(@NotNull @Positive(message = "Accident Coefficient must be positive!") BigDecimal accidentCoef) {
        this.accidentCoef = accidentCoef;
    }

    public int getAccidentCoefId() {
        return accidentCoefId;
    }

    public void setAccidentCoefId(int accidentCoefId) {
        this.accidentCoefId = accidentCoefId;
    }

    public BigDecimal getAccidentCoef() {
        return accidentCoef;
    }

    public void setAccidentCoef(BigDecimal accidentCoef) {
        this.accidentCoef = accidentCoef;
    }
}
