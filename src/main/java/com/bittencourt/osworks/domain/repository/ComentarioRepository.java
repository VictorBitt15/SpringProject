package com.bittencourt.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bittencourt.osworks.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
