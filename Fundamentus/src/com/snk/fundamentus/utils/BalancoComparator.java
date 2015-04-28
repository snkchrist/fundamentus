package com.snk.fundamentus.utils;

import java.util.Comparator;

import com.snk.fundamentus.models.BalancoPatrimonial;

public class BalancoComparator implements Comparator<BalancoPatrimonial> {
    @Override
    public int compare(final BalancoPatrimonial arg0, final BalancoPatrimonial arg1) {
        return arg0.getDataDoBalanco().compareTo(arg1.getDataDoBalanco());
    }
}
