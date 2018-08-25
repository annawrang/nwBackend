package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagDTO;

@Repository
public interface CountryTagRepository extends JpaRepository<CountryTagDTO, Long> {
}
