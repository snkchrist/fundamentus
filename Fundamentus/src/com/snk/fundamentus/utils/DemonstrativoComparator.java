package com.snk.fundamentus.utils;

import java.util.Comparator;

import com.snk.fundamentus.models.DemonstrativoResultado;

public class DemonstrativoComparator implements Comparator<DemonstrativoResultado> {

    @Override
    public int compare(final DemonstrativoResultado o1, final DemonstrativoResultado o2) {
        return o1.getDataDemonstrativo().compareTo(o2.getDataDemonstrativo());
    }
}
