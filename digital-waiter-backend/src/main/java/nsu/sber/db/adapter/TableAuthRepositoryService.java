package nsu.sber.db.adapter;

import lombok.RequiredArgsConstructor;
import nsu.sber.db.entity.TableAuthEntity;
import nsu.sber.db.mapper.TableAuthEntityMapper;
import nsu.sber.db.repository.TableAuthRepository;
import nsu.sber.domain.model.TableAuth;
import nsu.sber.domain.port.TableAuthRepositoryPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableAuthRepositoryService implements TableAuthRepositoryPort {

    private final TableAuthRepository tableAuthRepository;
    private final TableAuthEntityMapper tableAuthEntityMapper;

    @Override
    public TableAuth findByLogin(String login) {
        TableAuthEntity tableAuthEntity = tableAuthRepository.findByLogin(login);
        return tableAuthEntityMapper.entityToTableAuth(tableAuthEntity);
    }

    @Override
    public TableAuth findById(int id) {
        TableAuthEntity tableAuthEntity = tableAuthRepository.findById(id);
        return tableAuthEntityMapper.entityToTableAuth(tableAuthEntity);
    }
}
