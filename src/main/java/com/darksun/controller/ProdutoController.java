package com.darksun.controller;

import com.darksun.model.Produto;
import com.darksun.model.type.Categoria;
import com.darksun.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        return new ResponseEntity<>(service.create(produto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Produto> update(@RequestBody Produto produto) {
        return new ResponseEntity<>(service.update(produto), HttpStatus.OK);
    }

    @GetMapping("/{id}/calcular-preco")
    public ResponseEntity<Double> CalcularPreco(@RequestBody Produto produto) {
        return new ResponseEntity<>(service.calcularPreco(produto), HttpStatus.OK);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Produto>> findByCategoria(@RequestParam Categoria categoria) {
        return new ResponseEntity<>(service.findByCategoria(categoria), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
