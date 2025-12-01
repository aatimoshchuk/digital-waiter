package nsu.sber.web.mapper;

import javax.annotation.processing.Generated;
import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.web.dto.SignInRequestDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class AuthDtoMapperImpl implements AuthDtoMapper {

    @Override
    public SignInRequest dtoToSignInRequest(SignInRequestDto signInRequestDto) {
        if ( signInRequestDto == null ) {
            return null;
        }

        SignInRequest signInRequest = new SignInRequest();

        signInRequest.setLogin( signInRequestDto.getLogin() );
        signInRequest.setPassword( signInRequestDto.getPassword() );

        return signInRequest;
    }
}
