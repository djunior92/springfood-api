package com.course.springfood.domain.repository;

import com.course.springfood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);

}