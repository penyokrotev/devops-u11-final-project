package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "tax_amount")
public class TaxAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_amount_id")
    private int taxAmountId;

    @Column(name = "tax")
    @Positive
    private BigDecimal taxAmount;

    public TaxAmount() {

    }

    public TaxAmount(@Positive BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public int getTaxAmountId() {
        return taxAmountId;
    }

    public void setTaxAmountId(int taxAmountId) {
        this.taxAmountId = taxAmountId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
}
