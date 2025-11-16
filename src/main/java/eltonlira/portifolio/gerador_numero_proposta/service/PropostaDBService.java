package eltonlira.portifolio.gerador_numero_proposta.service;

import org.springframework.stereotype.Service;

@Service
public class PropostaDBService {

    private Long valorDoBanco = null; // simulação

    public Long BuscarPropostaMaisRecenteNoDB() {
        if (valorDoBanco == null) {
            valorDoBanco = 10000200L; // viria do BD real
        }
        return valorDoBanco;
    }

    public void salvarNovoValor(Long novoValor) {
        valorDoBanco = novoValor;
    }
}

