package com.darksun.repository;

import com.darksun.model.LinhaReceita;
import com.darksun.model.LinhaReceitaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhaReceitaRepository extends JpaRepository<LinhaReceita, LinhaReceitaId> {
    List<LinhaReceita> findByProdutoId(Long produtoId);

}
