package eltonlira.portifolio.gerador_numero_proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GeradorNumeroPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeradorNumeroPropostaApplication.class, args);
	}

}
