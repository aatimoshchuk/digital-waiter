package nsu.sber.messaging.pos.iiko.client;

import nsu.sber.messaging.pos.iiko.dto.AuthRequestDto;
import nsu.sber.messaging.pos.iiko.dto.AuthResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "iiko-client",
        contextId = "iikoAuthClient",
        url = "${iiko.url}"
)
public interface IikoAuthClient {

    @PostMapping("/api/1/access_token")
    AuthResponseDto getToken(@RequestBody AuthRequestDto authRequestDto);

}
