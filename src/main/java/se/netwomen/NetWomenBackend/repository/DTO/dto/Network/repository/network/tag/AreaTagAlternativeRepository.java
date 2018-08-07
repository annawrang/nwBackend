package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.AreaTagAlternativeDTO;

import java.util.Optional;

    @Repository
    public interface AreaTagAlternativeRepository extends JpaRepository<AreaTagAlternativeDTO, Long> {
        Optional<AreaTagAlternativeDTO> findByName(String name);
}


