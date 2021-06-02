package com.ramiro.poclayoutcomprovante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.poclayoutcomprovante.model.Comprovante;

import java.util.List;

public interface ComprovanteRepository extends JpaRepository<Comprovante, Long> {

    List<Comprovante> findByTipoIgnoreCaseAndVersaoIgnoreCase(String tipo, String versao);

}
