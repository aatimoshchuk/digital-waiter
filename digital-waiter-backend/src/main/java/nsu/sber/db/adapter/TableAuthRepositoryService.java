package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.mapper.TableAuthEntityMapper;
import nsu.sber.db.repository.jpa.TableAuthRepository;
import nsu.sber.domain.model.entity.TableAuth;
import nsu.sber.domain.port.repository.jpa.TableAuthRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableAuthRepositoryService implements TableAuthRepositoryPort {

    private final TableAuthRepository tableAuthRepository;
    private final TableAuthEntityMapper tableAuthEntityMapper;

    @Override
    public Optional<TableAuth> findByLogin(String login) {
        return tableAuthRepository.findByLogin(login)
                .map(tableAuthEntityMapper::entityToTableAuth);
    }

    @Override
    public Optional<TableAuth> findById(int id) {
        return tableAuthRepository.findById(id)
                .map(tableAuthEntityMapper::entityToTableAuth);
    }
}
