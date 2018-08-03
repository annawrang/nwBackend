package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.ForTagDTO;

@Repository
public interface ForTagRepository extends CrudRepository<ForTagDTO, Long> {
}
