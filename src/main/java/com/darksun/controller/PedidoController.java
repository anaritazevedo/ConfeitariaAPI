package com.darksun.controller;

import com.darksun.model.Pedido;
import com.darksun.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(service.create(pedido), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/valor-total")
    public ResponseEntity<Double> getValortotal(@PathVariable Long id, @RequestParam Integer desconto) {
        return new ResponseEntity<>(service.getValorTotal(id, desconto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Pedido> update(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(service.update(pedido), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
