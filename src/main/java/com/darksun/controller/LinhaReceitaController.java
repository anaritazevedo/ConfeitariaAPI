package com.darksun.controller;

import com.darksun.model.LinhaReceita;
import com.darksun.service.LinhaReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/linha-receita")
public class LinhaReceitaController {
    @Autowired
    LinhaReceitaService service;

    @PostMapping
    public ResponseEntity<LinhaReceita> create(@RequestBody LinhaReceita receita) {
        return new ResponseEntity<>(service.create(receita), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LinhaReceita>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<LinhaReceita> addItem(@RequestParam Long produtoId, @RequestParam Long ingredienteId) {
        return new ResponseEntity<>(service.addItem(produtoId, ingredienteId), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<LinhaReceita> removeItem(@RequestParam Long produtoId, @RequestParam Long ingredienteId) {
        return new ResponseEntity<>(service.removeItem(produtoId, ingredienteId), HttpStatus.OK);
    }
}
