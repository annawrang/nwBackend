package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.model.data.Network;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import java.util.Optional;

@Repository
public interface NetworkRepository extends JpaRepository<NetworkDTO, Long> {
    Optional<NetworkDTO> findByNetworkNumber(String networkNumber);


}
