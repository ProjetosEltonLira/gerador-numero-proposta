package eltonlira.portifolio.gerador_numero_proposta.controller;

import com.hazelcast.core.HazelcastInstance;
import eltonlira.portifolio.gerador_numero_proposta.model.Proposta;
import eltonlira.portifolio.gerador_numero_proposta.service.PropostaService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class PropostaController {

    private final PropostaService propostaService;
    private final HazelcastInstance hazelcastInstance;

    public PropostaController(PropostaService propostaService,
                              HazelcastInstance hazelcastInstance) {
        this.propostaService = propostaService;
        this.hazelcastInstance = hazelcastInstance;
    }

    // Usa o cache com @Cacheable
    @GetMapping("/{id}")
    public Proposta getProduto(@PathVariable Long id) {
        return propostaService.buscarProdutoPorId(id);
    }

    // Exemplo de uso direto do Hazelcast (mapa distribuído)
    @PostMapping("/direto/{id}")
    public String salvarDireto(@PathVariable Long id, @RequestParam String valor) {
        Map<Long, String> mapa = hazelcastInstance.getMap("mapa-direto");
        mapa.put(id, valor);
        return "Salvo no mapa-direto: [" + id + " -> " + valor + "]";
    }

    @GetMapping("/direto/{id}")
    public String buscarDireto(@PathVariable Long id) {
        Map<Long, String> mapa = hazelcastInstance.getMap("mapa-direto");
        String valor = mapa.get(id);
        return "Valor no mapa-direto: " + valor;
    }
}
