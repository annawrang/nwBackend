package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CityTagDTO;

@Repository
public interface CityTagRepository extends CrudRepository<CityTagDTO, Long> {
}
