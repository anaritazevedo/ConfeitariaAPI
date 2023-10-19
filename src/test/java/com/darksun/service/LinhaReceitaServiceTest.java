package com.darksun.service;

import com.darksun.model.*;
import com.darksun.repository.LinhaReceitaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.darksun.model.type.Categoria.BOLO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinhaReceitaServiceTest {
    @InjectMocks
    LinhaReceitaService service;
    @Mock
    LinhaReceitaRepository repository;
    @Mock
    IngredienteService ingredienteService;
    @Mock
    ProdutoService produtoService;

    List<Ingrediente> ingredienteLista;
    List<LinhaReceita> linhaReceitaLista;
    List<Pedido> pedidoLista;
    List<Produto> produtoLista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoLista = new ArrayList<>();
        Pedido pedido1 = new Pedido(1L, "Marcola", 20., LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());

        produtoLista = new ArrayList<>();
        Produto bolo = new Produto(2L, "Bolo de chocolate", "Bolo de chocolate com chocolate", 15., BOLO, new ArrayList<>());
        produtoLista.add(bolo);

        ingredienteLista = new ArrayList<>();
        Ingrediente farinha = new Ingrediente(3L, "Farinha de trigo", 5.);
        Ingrediente ovo = new Ingrediente(4L, "Ovo de galinha criada no sítio do João", 5.);
        ingredienteLista.add(farinha);
        ingredienteLista.add(ovo);

        linhaReceitaLista = new ArrayList<>();
        //criar linha receita id, olhar test utils
        LinhaReceita umaColherDeFarinha = new LinhaReceita(null, 1, farinha, bolo);
        LinhaReceita umOvo = new LinhaReceita(null, 1, ovo, bolo);
        LinhaReceitaId umaColherDeFarinhaId = new LinhaReceitaId(2L, 3L);
        LinhaReceitaId umOvoId = new LinhaReceitaId(2L, 4L);
        umaColherDeFarinha.setId(umaColherDeFarinhaId);
        umOvo.setId(umOvoId);
        linhaReceitaLista.add(umaColherDeFarinha);
        linhaReceitaLista.add(umOvo);

    }

    @Test
    void create() {
        LinhaReceita linhaReceita = new LinhaReceita();
        when(repository.save(linhaReceita)).thenReturn(linhaReceitaLista.get(0));
        LinhaReceita response = service.create(linhaReceita);
        Assertions.assertEquals(linhaReceitaLista.get(0), response);
        verify(repository, times(1)).save(any());
    }

    @Test
    void readAll() {
        when(repository.findAll()).thenReturn(linhaReceitaLista);
        List<LinhaReceita> linhaReceitas = service.readAll();
        Assertions.assertEquals(linhaReceitaLista, linhaReceitas);
        verify(repository, times(1)).findAll();
    }

    @Test
    void readById() {
        List<LinhaReceita> listaFarinha = new ArrayList<>();
        listaFarinha.add(linhaReceitaLista.get(0));
        when(repository.findByProdutoId(any())).thenReturn(
                listaFarinha);
        List<LinhaReceita> linhaReceitas = service.readByProdutoId(3L);
        Assertions.assertEquals(listaFarinha, linhaReceitas);
        verify(repository, times(1)).findByProdutoId(any());
    }

    @Test
    void addItem() {
        List<LinhaReceita> listaFarinha = new ArrayList<>();
        listaFarinha.add(linhaReceitaLista.get(0));
        when(repository.findByProdutoId(any())).thenReturn(listaFarinha);
        when(repository.save(any())).thenReturn(linhaReceitaLista.get(0));
        LinhaReceita response = service.addItem(2L, 3L);
        Assertions.assertEquals(2, response.getQuantidade());
        Assertions.assertEquals(2L, response.getId().getProdutoId());
        Assertions.assertEquals(3L, response.getId().getIngredienteId());
        verify(repository, times(1)).findByProdutoId(any());
        verify(repository, times(1)).save(any());

    }

    @Test
    void removeItem() {
        List<LinhaReceita> listaFarinha = new ArrayList<>();
        listaFarinha.add(linhaReceitaLista.get(0));
        when(repository.findByProdutoId(any())).thenReturn(listaFarinha);
        doNothing().when(repository).deleteById(any());
        service.removeItem(2L, 3L);
        verify(repository, times(1)).delete(any());
    }
}