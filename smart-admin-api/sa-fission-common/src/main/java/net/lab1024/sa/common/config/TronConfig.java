package net.lab1024.sa.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.common.common.wallet.EthClient;
import net.lab1024.sa.common.common.wallet.TronClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
public class TronConfig {

    @Value("${tron.isMainNet}")
    private boolean isMainNet;

    @Value("${tron.contractAddress}")
    private String contractAddress;

    @Bean
    public TronClient initTronClient() {
        return new TronClient(isMainNet, contractAddress);
    }
}
