package com.darksun.service;

import com.darksun.model.Ingrediente;
import com.darksun.model.LinhaReceita;
import com.darksun.model.LinhaReceitaId;
import com.darksun.model.Produto;
import com.darksun.repository.LinhaReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LinhaReceitaService {
    @Autowired
    LinhaReceitaRepository repository;
    @Autowired
    IngredienteService ingredienteService;
    @Autowired
    ProdutoService produtoService;

    public LinhaReceita create(LinhaReceita linhaReceita) {
        return repository.save(linhaReceita);
    }

    public List<LinhaReceita> readAll() {
        return repository.findAll();
    }

    public List<LinhaReceita> readByProdutoId(Long produtoId) {
        return repository.findByProdutoId(produtoId);
    }

    public LinhaReceita addItem(Long produtoId, Long ingredienteId) {
        LinhaReceita linhaReceita = null;
        List<LinhaReceita> linhaReceitas = readByProdutoId(produtoId);
        for (LinhaReceita receita : linhaReceitas) {
            if (receita.getProduto().getId().equals(produtoId)) {
                linhaReceita = receita;
                linhaReceita.setQuantidade(linhaReceita.getQuantidade() + 1);
                break;
            }
        }
        if (linhaReceita == null) {
            LinhaReceitaId linhaReceitaId = new LinhaReceitaId(produtoId, ingredienteId);
            Produto produto = produtoService.readById(produtoId);
            Ingrediente ingrediente = ingredienteService.readById(ingredienteId);
            linhaReceita = new LinhaReceita(linhaReceitaId, 1, ingrediente, produto);
        }
        return repository.save(linhaReceita);
    }

    public LinhaReceita removeItem(Long produtoId, Long ingredienteId) {
        LinhaReceita linhaReceita = null;
        List<LinhaReceita> linhaReceitas = readByProdutoId(produtoId);
        for (LinhaReceita receita : linhaReceitas) {
            if (receita.getIngrediente().getId().equals(ingredienteId)) {
                linhaReceita = receita;
                linhaReceita.setQuantidade(linhaReceita.getQuantidade() - 1);
                if (linhaReceita.getQuantidade() < 1) {
                    repository.delete(receita);
                } else {
                    repository.save(linhaReceita);
                }
                break;
            }
        }
        if (linhaReceita == null) {
            throw new EntityNotFoundException("Este item ainda não foi adicionado.");
        }
        return linhaReceita;
    }
}
