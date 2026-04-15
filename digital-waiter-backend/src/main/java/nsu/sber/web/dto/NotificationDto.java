package nsu.sber.web.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;

    private String text;

    private String pullToken;
}
