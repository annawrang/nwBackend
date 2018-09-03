package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Role;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.resource.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;
import se.netwomen.NetWomenBackend.service.Parsers.UserParser;
import se.netwomen.NetWomenBackend.service.exceptions.CustomException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final NetworkRepository networkRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository repository, ProfileRepository profileRepository, NetworkRepository networkRepository) {
        this.userRepository = repository;
        this.profileRepository = profileRepository;
        this.networkRepository = networkRepository;
    }

    public boolean saveNewUser(User user) {
        if (user.getUserNumber() == null) {
            user.setUserNumber(UUID.randomUUID().toString());
        }
        UserDTO userDTO = UserParser.toUserDTO(user);
        userDTO = userRepository.save(userDTO);
        if (createNewProfileForUser(userDTO)){
            return true;
        }
        return false;
    }

    private boolean createNewProfileForUser(UserDTO userDTO) {
        ProfileDTO profile = new ProfileDTO(userDTO);
        profile = profileRepository.save(profile);
        if(profile.getId() != null){
            return true;
        } return false;
    }

    public User findByEmailAndPassword(String email, String password) {
        Optional<UserDTO> userDTO = userRepository.findByEmailAndPassword(email, password);
        if (userDTO.isPresent()) {
            return UserParser.toUser(userDTO.get());
        }
        return null;
    }


    public String signIn(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, userRepository.findByEmailAndPassword(email, password).get().getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void addNetworkToUser(String userNumber, Network network) {
        Optional<UserDTO> userDTO = userRepository.findOneWithNetworkDTOsByUserNumber(userNumber);
        Optional<NetworkDTO> networkDTO = networkRepository.findByNetworkNumber(network.getNetworkNumber());
        networkDTO.orElseThrow(NotFoundException::new);
        userDTO.orElseThrow(NotFoundException::new);
        userDTO.get().addNetworkDTO(networkDTO.get());
        userRepository.save(userDTO.get());
    }

    public List<Network> findMyNetworksForUser(String userNumber, NetworkParam networkParam){
        Page<NetworkDTO> networkDTOs = networkRepository.findByUsersUserNumber(userNumber, getPageRequest(networkParam));
        return NetworkParser.parseNetworkEntities(networkDTOs.getContent());
    }

    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }


}
