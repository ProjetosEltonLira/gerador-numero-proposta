package eltonlira.portifolio.gerador_numero_proposta.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();

        config.setInstanceName("hazelcast-demo-instance");
        config.setClusterName("meu-cluster-hazelcast");

        // Network / Cluster
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortAutoIncrement(true);

        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false); // desabilita multicast
        join.getTcpIpConfig()
                .setEnabled(true)
                // Todos os nós/hosts que podem participar do cluster.
                // Se for tudo local, você pode usar localhost, 127.0.0.1 etc.
                .addMember("127.0.0.1"); // pode adicionar outros IPs se for em VMs/containers

        // Configuração de um mapa distribuído específico (para @Cacheable("produtos"))
        MapConfig produtosMapConfig = new MapConfig("produtos");
        produtosMapConfig
                .setTimeToLiveSeconds(60);//expira em 60 segundos

        config.addMapConfig(produtosMapConfig);

        return config;
    }
}
