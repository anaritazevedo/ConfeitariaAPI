package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Embeddable
public class LinhaReceitaId implements Serializable {
    @Serial
    private static final long serialVersionUID = -6016390183490870578L;
    private Long produtoId;
    private Long ingredienteId;


}
