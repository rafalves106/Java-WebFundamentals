/**
 * @author falvesmac
 */

package br.com.falves.Projeto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Status {
    private TipoStatus tipo;
    private String texto;
}