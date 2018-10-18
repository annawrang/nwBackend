package se.netwomen.NetWomenBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.TagUpdateAlternative;
import se.netwomen.NetWomenBackend.service.NetworkService;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Set;

@RestController
@RequestMapping(value = "/tags")
public class TagController {
    private final NetworkService networkService;

    public TagController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @PostMapping(path = "/country")
    public ResponseEntity createCountryTag(@RequestBody Set<CountryTagAlternative> countryTag) {
        networkService.saveCountries(countryTag);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/area")
    public ResponseEntity connectAreaTagAlternativeToCountryTagAlternative(@RequestBody TagUpdateAlternative countryTagUpdate) {
        networkService.connectAreaTagAlternativeToCountryTagAlternative(countryTagUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/for")
    public ResponseEntity createForTag(@RequestBody ForTag forTag) {
        networkService.saveForTag(forTag);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/alternatives")
    public ResponseEntity getAlternativeTags() {
        return new ResponseEntity(networkService.getAlternativeTags(), HttpStatus.OK);
    }

    @GetMapping(path = "/used")
    public ResponseEntity getUsedTags() {
        return new ResponseEntity(networkService.getUsedTags(), HttpStatus.OK);
    }


    @GetMapping(path = "{countryName}")
    public ResponseEntity findAreaTagAlternativesFromCountryName(@PathVariable String country) {
        return new ResponseEntity(networkService.findAreaTagAlternativesFromCountryName(country), HttpStatus.OK);
    }
}
