package eltonlira.portifolio.gerador_numero_proposta.dto;

import java.io.Serializable;
import java.util.UUID;

public class Proposta implements Serializable {

    UUID idJornada;
    Long numeroProposta;

    public Proposta(){};
    public Proposta(UUID idJornada, Long numeroProposta) {
        this.idJornada = idJornada;
        this.numeroProposta = numeroProposta;
    }

    public UUID getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(UUID idJornada) {
        this.idJornada = idJornada;
    }

    public Long getNumeroProposta() {
        return numeroProposta;
    }

    public void setNumeroProposta(Long numeroProposta) {
        this.numeroProposta = numeroProposta;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "idJornada=" + idJornada +
                ", numeroProposta=" + numeroProposta +
                '}';
    }
}
