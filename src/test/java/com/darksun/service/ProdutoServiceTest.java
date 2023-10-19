package com.darksun.service;

import com.darksun.model.Ingrediente;
import com.darksun.model.LinhaReceita;
import com.darksun.model.Pedido;
import com.darksun.model.Produto;
import com.darksun.repository.ProdutoRepository;
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

class ProdutoServiceTest {
    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    List<Produto> produtoLista;
    List<Produto> boloLista;
    List<Pedido> pedidoLista;
    List<Ingrediente> ingredienteLista;
    List<LinhaReceita> linhaReceitaLista;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoLista = new ArrayList<>();
        boloLista = new ArrayList<>();

        Pedido pedido1 = new Pedido(1L, "Marcola", 20., LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());

        produtoLista = new ArrayList<>();
        Produto bolo = new Produto(2L, "Bolo de chocolate", "Bolo de chocolate com chocolate", 15., BOLO, new ArrayList<>());
        produtoLista.add(bolo);
        boloLista.add(bolo);


        ingredienteLista = new ArrayList<>();
        Ingrediente farinha = new Ingrediente(3L, "Farinha de trigo", 5.);
        Ingrediente ovo = new Ingrediente(4L, "Ovo de galinha criada no sítio do João", 5.);
        ingredienteLista.add(farinha);
        ingredienteLista.add(ovo);

        linhaReceitaLista = new ArrayList<>();
        //criar linha receita id, olhar test utils
        LinhaReceita umaColherDeFarinha = new LinhaReceita(null, 1, farinha, bolo);
        LinhaReceita umOvo = new LinhaReceita(null, 1, ovo, bolo);
        linhaReceitaLista.add(umaColherDeFarinha);
        linhaReceitaLista.add(umOvo);
        bolo.setReceita(linhaReceitaLista);

    }

    @Test
    void create() {
        Produto produto = new Produto(0L, "Bolo", "Bolinho feito com muito bolo sabor bolo", 12., BOLO, new ArrayList<>());
        when(repository.save(produto)).thenReturn(produtoLista.get(0));
        Produto response = service.create(produto);
        Assertions.assertEquals(produtoLista.get(0), response);
        verify(repository, times(1)).save(any());
    }

    @Test
    void readAll() {
        when(repository.findAll()).thenReturn(produtoLista);
        List<Produto> produtos = service.readAll();
        Assertions.assertEquals(produtoLista, produtos);
        verify(repository, times(1)).findAll();
    }

    @Test
    void readById() {
        when(repository.findById(any())).thenReturn(
                Optional.ofNullable(produtoLista.get(0)));
        Produto produtos = service.readById(1L);
        Assertions.assertEquals(produtoLista.get(0), produtos);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void update() {
        Produto produto = produtoLista.get(0);
        String newDescricao = "pipipi popopo";
        produto.setDescricao(newDescricao);
        when(repository.save(produto)).thenReturn(produto);
        Produto response = service.update(produto);
        Assertions.assertEquals(newDescricao, response.getDescricao());
        verify(repository, times(1)).save(any());
    }

    @Test
    void calcularPreco() {
        Double response = service.calcularPreco(produtoLista.get(0));
        Assertions.assertEquals(10., response);
    }

    @Test
    void findByCategoria() {
        when(repository.findByCategoria(any())).thenReturn(
                boloLista);
        List<Produto> produtos = service.findByCategoria(BOLO);
        Assertions.assertEquals(produtos.get(0), boloLista.get(0));
        Assertions.assertEquals(produtos.size(), boloLista.size());
        verify(repository, times(1)).findByCategoria(any());
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(any());
        service.delete(1L);
        verify(repository, times(1)).deleteById(any());
    }
}