package net.lab1024.sa.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.common.common.wallet.TronXClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
public class TronConfig {

    @Value("${tron.isMainNet}")
    private boolean isMainNet;

    @Value("${tron.apiKey}")
    private String apiKey;

    @Bean
    public TronXClient initTronXClient() {
        return new TronXClient(isMainNet, apiKey);
    }
}
