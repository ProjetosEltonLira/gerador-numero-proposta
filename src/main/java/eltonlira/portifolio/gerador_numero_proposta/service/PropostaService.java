package eltonlira.portifolio.gerador_numero_proposta.service;

import eltonlira.portifolio.gerador_numero_proposta.model.Proposta;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    @Cacheable("Proposta") // usa o Map distribuído "produtos"
    public Proposta buscarProdutoPorId(Long id) {
        // Simula uma operação demorada (ex.: acesso a banco, API externa)
        simularDemora();
        Long idRandomico = (long) Math.random();

        return new Proposta(idRandomico);
    }

    private void simularDemora() {
        try {
            Thread.sleep(2000); // 2 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
