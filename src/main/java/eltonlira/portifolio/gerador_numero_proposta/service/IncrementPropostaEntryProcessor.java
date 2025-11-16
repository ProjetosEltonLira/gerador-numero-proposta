package eltonlira.portifolio.gerador_numero_proposta.service;

import com.hazelcast.map.EntryProcessor;

import java.io.Serializable;
import java.util.Map;

public class IncrementPropostaEntryProcessor
        implements EntryProcessor<String, Long, Long>, Serializable {

    @Override
    public Long process(Map.Entry<String, Long> entry) {
        Long value = entry.getValue();
        if (value == null) return null;

        long next = value + 1;
        entry.setValue(next);
        return next;
    }
}


