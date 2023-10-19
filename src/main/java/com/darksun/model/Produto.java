package com.darksun.model;

import com.darksun.model.type.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties
public class Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6652394652002496515L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Categoria categoria;
    @JsonIgnoreProperties("produto")
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<LinhaReceita> receita;
}
