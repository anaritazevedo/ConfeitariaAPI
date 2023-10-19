package com.darksun.service;

import com.darksun.model.Ingrediente;
import com.darksun.repository.IngredienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IngredienteServiceTest {

    @InjectMocks
    private IngredienteService service;

    @Mock
    private IngredienteRepository repository;

    List<Ingrediente> ingredienteLista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredienteLista = new ArrayList<>();
        Ingrediente massa = new Ingrediente(1L, "Farinha", 13.);
        Ingrediente chocolate = new Ingrediente(2L, "Leite de cabra", 11.);
        ingredienteLista.add(massa);
        ingredienteLista.add(chocolate);
    }

    @Test
    void create() {
        Ingrediente ingrediente = new Ingrediente(0L, "Arroz", 12.);
        when(repository.save(ingrediente)).thenReturn(ingredienteLista.get(0));
        Ingrediente response = service.create(ingrediente);
        Assertions.assertEquals(ingredienteLista.get(0), response);
        verify(repository, times(1)).save(any());
    }

    @Test
    void readAll() {
        when(repository.findAll()).thenReturn(ingredienteLista);
        List<Ingrediente> ingredientes = service.readAll();
        Assertions.assertEquals(ingredienteLista, ingredientes);
        verify(repository, times(1)).findAll();
    }

    @Test
    void readById() {
        when(repository.findById(any())).thenReturn(
                Optional.ofNullable(ingredienteLista.get(0)));
        Ingrediente ingredientes = service.readById(1L);
        Assertions.assertEquals(ingredienteLista.get(0), ingredientes);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void update() {
        Ingrediente ingrediente = ingredienteLista.get(1);
        Double newPrecoPorGrama = 3.;
        ingrediente.setPrecoPorGrama(newPrecoPorGrama);
        when(repository.save(ingrediente)).thenReturn(ingrediente);
        Ingrediente response = service.update(ingrediente);
        Assertions.assertEquals(newPrecoPorGrama, response.getPrecoPorGrama());
        verify(repository, times(1)).save(any());
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(any());
        service.delete(1L);
        verify(repository, times(1)).deleteById(any());
    }
}