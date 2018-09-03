package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface NetworkRepository extends JpaRepository<NetworkDTO, Long> {
    Optional<NetworkDTO> findByNetworkNumber(String networkNumber);
    Page<NetworkDTO> findByCountryTagsName(String name, Pageable pageable);
    Page<NetworkDTO> findByForTagsName(String name, Pageable pageable);
    Page<NetworkDTO> findDistinctByForTagsNameInAndCountryTagsName(String forTagName, String countryTagName, Pageable pageable);
    Page<NetworkDTO> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<NetworkDTO> findByUsersUserNumber(String userNumber, Pageable pageable);
    List<NetworkDTO> findByUsersUserNumber(String userNumber);
}
