package com.darksun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinhaReceita implements Serializable {

    @EmbeddedId
    private LinhaReceitaId id;
    private Integer quantidade;
    @ManyToOne
    @JsonIgnoreProperties("receita")
    @MapsId("ingredienteId")
    private Ingrediente ingrediente;

    @ManyToOne
    @JsonIgnoreProperties("receita")
    @MapsId("produtoId")
    private Produto produto;
}
