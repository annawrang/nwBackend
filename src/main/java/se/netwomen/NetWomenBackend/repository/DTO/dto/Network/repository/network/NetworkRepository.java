package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import java.util.Optional;

@Repository
public interface NetworkRepository extends JpaRepository<NetworkDTO, Long> {
    Optional<NetworkDTO> findByNetworkNumber(String networkNumber);


}
