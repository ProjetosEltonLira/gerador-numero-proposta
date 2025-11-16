package eltonlira.portifolio.gerador_numero_proposta.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import eltonlira.portifolio.gerador_numero_proposta.dto.PropostaDTORequest;
import eltonlira.portifolio.gerador_numero_proposta.dto.PropostaDTOResponse;
import org.springframework.stereotype.Service;


@Service
public class PropostaService {

    private final HazelcastInstance hazelcastInstance;
    private final PropostaDBService propostaDBService;

    public PropostaService(HazelcastInstance hazelcastInstance,
                           PropostaDBService propostaDBService) {
        this.hazelcastInstance = hazelcastInstance;
        this.propostaDBService = propostaDBService;
    }

    public PropostaDTOResponse gerarId(PropostaDTORequest request) {

        //acessa o cache sequencia-proposta
        IMap<String, Long> map = hazelcastInstance.getMap("sequencia-proposta");

        // 1. Verifica no cache
        Long valorAtual = map.get("contador");

        if (valorAtual == null) {
            // 2. Busca do banco
            Long inicial = propostaDBService.BuscarPropostaMaisRecenteNoDB();

            // 3. Atualiza cache
            map.put("contador", inicial);

            valorAtual = inicial;
        }

        // 4. Incrementa usando EntryProcessor (atomic)
        Long novoValor = map.executeOnKey(
                "contador",
                new IncrementPropostaEntryProcessor()
        );

        return new PropostaDTOResponse(request.idJornada(), novoValor);
    }
}








