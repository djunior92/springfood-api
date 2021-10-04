package com.course.springfood.domain.repository;

import com.course.springfood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

//	List<Cozinha> consultarPorNome(String nome);

}