package nsu.sber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetGuestCountResponseDto {

    @Schema(description = "The number of guests at the current table", example = "1")
    private Integer guestCount;

}
