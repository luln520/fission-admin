package net.lab1024.sa.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.common.common.wallet.EthereumClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
public class EthConfig {

    @Value("${eth.rpcUrl}")
    private String rpcUrl;

    @Bean
    public EthereumClient initEthereumClient() {
        return new EthereumClient(rpcUrl);
    }
}
