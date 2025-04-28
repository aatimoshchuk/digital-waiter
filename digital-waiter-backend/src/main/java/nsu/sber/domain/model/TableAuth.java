package nsu.sber.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableAuth {

    private Integer id;

    private String login;

    private String password;

}
