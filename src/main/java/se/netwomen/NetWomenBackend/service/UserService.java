package se.netwomen.NetWomenBackend.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.Role;
import se.netwomen.NetWomenBackend.model.data.User;
<<<<<<< HEAD
import se.netwomen.NetWomenBackend.model.data.network.Network;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.repository.network.NetworkRepository;
=======
>>>>>>> master
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.ProfileDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Profile.profile.ProfileRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
<<<<<<< HEAD
import se.netwomen.NetWomenBackend.resource.param.NetworkParam;
import se.netwomen.NetWomenBackend.resource.security.JwtTokenProvider;
import se.netwomen.NetWomenBackend.service.Parsers.NetworkParser;
=======
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.controller.security.JwtTokenProvider;
>>>>>>> master
import se.netwomen.NetWomenBackend.service.Parsers.UserParser;
import se.netwomen.NetWomenBackend.service.exceptions.CustomException;
import se.netwomen.NetWomenBackend.service.logic.UserLogic;

<<<<<<< HEAD
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
=======
>>>>>>> master
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
<<<<<<< HEAD
    private final NetworkRepository networkRepository;
=======
    private final UserLogic userLogic;
>>>>>>> master

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
<<<<<<< HEAD
    public UserService(UserRepository repository, ProfileRepository profileRepository, NetworkRepository networkRepository) {
        this.userRepository = repository;
        this.profileRepository = profileRepository;
        this.networkRepository = networkRepository;
=======
    public UserService(UserRepository repository, ProfileRepository profileRepository, UserLogic userLogic) {
        this.userRepository = repository;
        this.profileRepository = profileRepository;
        this.userLogic = userLogic;
>>>>>>> master
    }

    public boolean saveNewUser(User user) {
        this.userLogic.validateEmailDoesntExists(user.getEmail());
        user.setUserNumber(UUID.randomUUID().toString());
        UserDTO userDTO = userRepository.save(UserParser.toUserDTO(user));
        return createNewProfileForUser(userDTO);
    }

    private boolean createNewProfileForUser(UserDTO userDTO) {
        ProfileDTO profile = new ProfileDTO(userDTO);
        profile = profileRepository.save(profile);
        return (profile.getId() != null);
    }

    public User findByEmailAndPassword(String email, String password) {
        return UserParser.toUser(userLogic.validateUserByEmailPassword(email, password));
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
        UserDTO userDTO = userRepository.findOneWithNetworkDTOsByUserNumber(userNumber)
                .orElseThrow(NotFoundException::new);
        NetworkDTO networkDTO = networkRepository.findByNetworkNumber(network.getNetworkNumber())
                .orElseThrow(NotFoundException::new);
        userDTO.addNetworkDTO(networkDTO);
        userRepository.save(userDTO);
    }

    public List<Network> findMyNetworksForUser(String userNumber, NetworkParam networkParam){
        Page<NetworkDTO> networkDTOs = networkRepository.findByUsersUserNumber(userNumber, getPageRequest(networkParam));
        return NetworkParser.parseNetworkEntities(networkDTOs.getContent());
    }

    private PageRequest getPageRequest(NetworkParam pageParam){
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }


}
