package com.darksun.service;

import com.darksun.model.Pedido;
import com.darksun.model.Produto;
import com.darksun.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository repository;
    @Autowired
    ProdutoService produtoService;

    public Pedido create(Pedido pedido) {
        return repository.save(pedido);
    }

    public List<Pedido> readAll() {
        return repository.findAll();
    }

    public Pedido readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Pedido não encontrado com o ID: " + id));
    }

    public Double getValorTotal(Long id, Integer desconto) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Pedido não encontrado com o ID: " + id));
        Double valorTotal = 0.0;
        for (Produto produto : pedido.getProdutos()) {
            valorTotal += produtoService.calcularPreco(produto);
        }
        Double precoFinal = valorTotal * (1 - (desconto / 100.0));
        return precoFinal;
    }

    public Pedido update(Pedido pedido) {
        if (pedido.getId() == null) {
            throw new RuntimeException("Esse pedido não existe");
        }
        return repository.save(pedido);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Produto com o id: " + id + "não encontrado!");
        }
    }
}
