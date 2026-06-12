package com.finpay.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="Wallet-client",url="${app.wallet.url}")
public interface WalletClient {
    @PostMapping("/api/v1/wallets")
    String triggerCreateWallet(@RequestBody Map<String, Object> requestBody);
}
