package com.darksun.controller;

import com.darksun.model.Ingrediente;
import com.darksun.service.IngredienteService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    @Autowired
    IngredienteService service;

    @PostMapping
    public ResponseEntity<Ingrediente> create(@RequestBody Ingrediente ingrediente) {
        return new ResponseEntity<>(service.create(ingrediente), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Ingrediente>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> readById(@PathVariable Long id){
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Ingrediente> update (@RequestBody Ingrediente ingrediente){
        return new ResponseEntity<>(service.update(ingrediente), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
