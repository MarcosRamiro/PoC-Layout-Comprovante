package com.ramiro.PoCLayoutComprovante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.PoCLayoutComprovante.model.Comprovante;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ComprovanteRepository extends JpaRepository<Comprovante, Long> {


    List<Comprovante> findByTipoIgnoreCaseAndVersaoIgnoreCase(String tipo, String versao);

}
