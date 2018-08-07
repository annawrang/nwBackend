package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.CountryTagAlternativeDTO;

import java.util.Optional;

@Repository
public interface CountryTagAlternativeRepository extends JpaRepository<CountryTagAlternativeDTO,Long> {
    Optional<CountryTagAlternativeDTO> findByName(String name);
}
