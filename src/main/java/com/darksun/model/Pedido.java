package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = -5600459916977035547L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCliente;
    private Double valorTotal;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pedido_produto", joinColumns = {
            @JoinColumn(name = "pedido_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "produto_id", nullable = false, updatable = false)})
    private List<Produto> produtos = new ArrayList<>();
}
