package com.possesor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rafal Piotrowicz on 15.01.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    private LocalDate date;
    private String base;
    private Rates rates;


    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Override
    public String toString(){
        return "Base: " + this.base +
                "Date: " + this.date +
                "Rates" + this.rates.getAdditionalProperties().entrySet().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
