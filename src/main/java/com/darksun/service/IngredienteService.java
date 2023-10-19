package com.darksun.service;

import com.darksun.model.Ingrediente;
import com.darksun.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class IngredienteService {
    @Autowired
    IngredienteRepository repository;

    public Ingrediente create(Ingrediente ingrediente) {
        if (ingrediente.getId() != 0) {
            throw new IllegalArgumentException("Este produto não está registrado!");
        }
        if (ingrediente.getNome() == null || ingrediente.getNome().trim().equals("")) {
            throw new IllegalArgumentException("Produto sem nome!");
        }
        if (ingrediente.getPrecoPorGrama() == null
                || ingrediente.getPrecoPorGrama() <= 0) {
            throw new IllegalArgumentException("Produto com preço inválido!");
        }
        return repository.save(ingrediente);
    }

    public List<Ingrediente> readAll() {
        return repository.findAll();
    }

    public Ingrediente readById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Ingrediente não encontrado no ID: " + id));
    }

    public Ingrediente update(Ingrediente ingrediente) {
        if (ingrediente.getId() == null) {
            throw new IllegalArgumentException("Este produto não possui ID!");
        }
        if (ingrediente.getNome() == null) {
            throw new IllegalArgumentException("Produto não possui nome!");
        }
        if (ingrediente.getPrecoPorGrama() == null
                || ingrediente.getPrecoPorGrama() <= 0) {
            throw new IllegalArgumentException("Produto com preço inválido!");
        }
        return repository.save(ingrediente);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            throw new EntityNotFoundException("Produto não encontrado pelo ID: " + id);
        }
    }
}






