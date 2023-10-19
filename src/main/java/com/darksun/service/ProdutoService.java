package com.darksun.service;

import com.darksun.model.LinhaReceita;
import com.darksun.model.Produto;
import com.darksun.model.type.Categoria;
import com.darksun.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    public Produto create(Produto produto) {
        if (produto.getId() != 0) {
            throw new IllegalArgumentException("Produto não registrado");
        }
        if (produto.getNome() == null || produto.getNome().trim().equals("")) {
            throw new IllegalArgumentException("Produto não possui nome");
        }
        if (produto.getPreco() == null
                || produto.getPreco() <= 0.) {
            throw new IllegalArgumentException("Produto com preço invalido");
        }
        return repository.save(produto);
    }

    public List<Produto> readAll() {
        return repository.findAll();
    }

    public Produto readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id de produto não encontrado: " + id));
    }

    public Produto update(Produto produto) {
        if (produto.getId() == null) {
            throw new IllegalArgumentException("Este produto não tem ID");
        }
        if (produto.getNome() == null) {
            throw new IllegalArgumentException("Este produto não tem nome");
        }
        if (produto.getPreco() == null
                || produto.getPreco() <= 0) {
            throw new IllegalArgumentException("Produto com preço inválido");
        }
        return repository.save(produto);
    }

    public Double calcularPreco(Produto produto) {
        List<LinhaReceita> receita = produto.getReceita();
        Double custoTotal = 0.0;
        for (LinhaReceita linhaReceita : receita) {
            Double custoIngrediente = linhaReceita.getIngrediente().getPrecoPorGrama() * linhaReceita.getQuantidade();
            custoTotal += custoIngrediente;
        }
        return custoTotal;
    }

    public List<Produto> findByCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Produto com id não encontrado: " + id);
        }
    }
}


