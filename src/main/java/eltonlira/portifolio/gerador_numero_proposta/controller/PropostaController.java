package eltonlira.portifolio.gerador_numero_proposta.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import eltonlira.portifolio.gerador_numero_proposta.dto.Data;
import eltonlira.portifolio.gerador_numero_proposta.dto.PropostaDTORequest;
import eltonlira.portifolio.gerador_numero_proposta.dto.PropostaDTOResponse;
import eltonlira.portifolio.gerador_numero_proposta.service.PropostaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;
    private final HazelcastInstance hazelcastInstance;

    public PropostaController(PropostaService propostaService,
                              HazelcastInstance hazelcastInstance) {
        this.propostaService = propostaService;
        this.hazelcastInstance = hazelcastInstance;
    }

    // Usa o cache com @Cacheable
    @PostMapping
    public ResponseEntity<Data<PropostaDTOResponse>> postProposta(
            @RequestBody PropostaDTORequest propostaDTORequest) {

        // chama o service e obtém a proposta gerada
        PropostaDTOResponse proposta = propostaService.gerarId(propostaDTORequest);

        // mapa tipado corretamente
        Map<UUID, PropostaDTOResponse> mapa = hazelcastInstance.getMap("mapa-proposta");

        // salva no Hazelcast
        mapa.put(proposta.getIdJornada(), proposta);

        // retorna usando Data
        return ResponseEntity.ok(new Data<>(proposta));
    }

    @GetMapping("/{idJornada}")
    public ResponseEntity<Data<PropostaDTOResponse>> getProposta(@PathVariable UUID idJornada) {

        //Está retornando oque está no cache
        Map<UUID, PropostaDTOResponse> mapa = hazelcastInstance.getMap("mapa-proposta");
        var proposta = mapa.get(idJornada);

        if (proposta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new Data<>(proposta));
    }

    @GetMapping
    public ResponseEntity<Data<List<PropostaDTOResponse>>> getPropostas() {

        Map<UUID, PropostaDTOResponse> mapa = hazelcastInstance.getMap("mapa-proposta");

        List<PropostaDTOResponse> lista = new ArrayList<>(mapa.values());

        // Ordenação decrescente por numeroProposta
        lista.sort(Comparator.comparing(PropostaDTOResponse::getNumeroProposta).reversed());

        return ResponseEntity.ok(new Data<>(lista));
    }

    @GetMapping("/sequencia-atual")
    public ResponseEntity<Data<IMap<String, Long>>> getSequenciaAtual() {

        IMap<String, Long> mapa = hazelcastInstance.getMap("sequencia-proposta");

        return ResponseEntity.ok(new Data<>(mapa));
    }
}
