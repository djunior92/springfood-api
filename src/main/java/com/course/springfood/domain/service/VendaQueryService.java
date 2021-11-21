package com.course.springfood.domain.service;

import com.course.springfood.domain.filter.VendaDiariaFilter;
import com.course.springfood.domain.model.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}
