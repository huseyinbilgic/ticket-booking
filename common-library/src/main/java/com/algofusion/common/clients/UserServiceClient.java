package com.algofusion.common.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/users")
public interface UserServiceClient {

    @GetMapping("/{id}")
    Boolean exitsUserById(@PathVariable Long id);
    
}
