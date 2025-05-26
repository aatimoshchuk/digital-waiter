package nsu.sber.messaging.pos.iiko.client;

import nsu.sber.messaging.pos.iiko.config.IikoFeignConfig;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "iiko-client",
        contextId = "iikoClient",
        url = "${iiko.url}",
        configuration = IikoFeignConfig.class
)
public interface IikoClient {

    @PostMapping("/api/2/menu/by_id")
    MenuResponseDto getMenu(@RequestHeader("Authorization") String token, @RequestBody MenuRequestDto menuRequestDto);

}
