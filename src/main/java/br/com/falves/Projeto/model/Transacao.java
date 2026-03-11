/**
 * @author falvesmac
 */

package br.com.falves.Projeto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    private Long id;
    private String descricao;
    private Double valor;
    private TipoTransacao tipo;
    private String data;
}