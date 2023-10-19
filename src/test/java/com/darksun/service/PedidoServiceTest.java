package com.darksun.service;

import com.darksun.model.*;
import com.darksun.repository.PedidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.darksun.model.type.Categoria.BOLO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository repository;

    @Mock
    private ProdutoService produtoService;
    List<Pedido> pedidoLista;

    List<Produto> produtoLista;
    List<Ingrediente> ingredienteLista;
    List<LinhaReceita> linhaReceitaLista;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoLista = new ArrayList<>();
        produtoLista = new ArrayList<>();
        ingredienteLista = new ArrayList<>();
        linhaReceitaLista = new ArrayList<>();


        Ingrediente farinha = new Ingrediente(3L, "Farinha de trigo", 5.);
        Ingrediente ovo = new Ingrediente(4L, "Ovo de galinha criada no sítio do João", 5.);
        ingredienteLista.add(farinha);
        ingredienteLista.add(ovo);

        Produto bolo = new Produto(2L, "Bolo de chocolate", "Bolo de chocolate com chocolate", 15., BOLO, new ArrayList<>());

        //criar linha receita id, olhar test utils
        LinhaReceita umaColherDeFarinha = new LinhaReceita(new LinhaReceitaId(3L, null), 1, farinha, bolo);
        LinhaReceita umOvo = new LinhaReceita(new LinhaReceitaId(4L, null), 1, ovo, bolo);


        umaColherDeFarinha.getId().setProdutoId(2L);
        umOvo.getId().setProdutoId(2L);
        linhaReceitaLista.add(umaColherDeFarinha);
        linhaReceitaLista.add(umOvo);
        bolo.setReceita(linhaReceitaLista);

        produtoLista.add(bolo);

        Pedido pedido1 = new Pedido(1L, "Marcola", 20., LocalDateTime.now(), LocalDateTime.now(), produtoLista);
        pedidoLista.add(pedido1);

    }

    @Test
    void create() {
        Pedido pedido = new Pedido(1L, "Marcola", 20., LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        when(repository.save(pedido)).thenReturn(pedidoLista.get(0));
        Pedido response = service.create(pedido);
        Assertions.assertEquals(pedidoLista.get(0), response);
        verify(repository, times(1)).save(any());
    }

    @Test
    void readAll() {
        when(repository.findAll()).thenReturn(pedidoLista);
        List<Pedido> pedidos = service.readAll();
        Assertions.assertEquals(pedidoLista, pedidos);
        verify(repository, times(1)).findAll();
    }

    @Test
    void readById() {
        when(repository.findById(any())).thenReturn(
                Optional.ofNullable(pedidoLista.get(0)));
        Pedido pedidos = service.readById(1L);
        Assertions.assertEquals(pedidoLista.get(0), pedidos);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void getValorTotal() {
        when(repository.findById(any())).thenReturn(
                Optional.ofNullable(pedidoLista.get(0)));
        when(produtoService.calcularPreco(any())).thenReturn(10.);
        Double response = service.getValorTotal(pedidoLista.get(0).getId(), 0);
        Assertions.assertEquals(10., response);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void update() {
        Pedido pedido = pedidoLista.get(0);
        pedido.setValorTotal(18.);
        when(repository.save(pedido)).thenReturn(pedido);
        Pedido response = service.update(pedido);
        Assertions.assertEquals("Marcola", response.getNomeCliente());
        Assertions.assertEquals(18., response.getValorTotal());
        verify(repository, times(1)).save(any());
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(any());
        service.delete(1L);
        verify(repository, times(1)).deleteById(any());
    }
}