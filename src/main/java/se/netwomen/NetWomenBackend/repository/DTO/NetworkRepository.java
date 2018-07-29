package se.netwomen.NetWomenBackend.repository.DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import se.netwomen.NetWomenBackend.model.data.Network;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import java.util.Optional;

public interface NetworkRepository extends CrudRepository<NetworkDTO, Long>{
    Optional<NetworkDTO> findByNetworkNumber(String networkNumber);
    Page<NetworkDTO> findAll(Pageable pageable);

}
