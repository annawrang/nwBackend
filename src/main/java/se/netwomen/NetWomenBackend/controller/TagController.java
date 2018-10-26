package se.netwomen.NetWomenBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
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

    @PostMapping(path = "for")
    public ResponseEntity createForTag(@RequestBody ForTag forTag) {
        networkService.saveForTag(forTag);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping(value = "for")
    public ResponseEntity getForTags(){
        return ResponseEntity.ok(networkService.getForTags());
    }

    @DeleteMapping(value = "for/{name}")
    public ResponseEntity delteForTags(@PathVariable("name") String name){
        networkService.deleteForTag(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity getUsedTags() {
        return new ResponseEntity(networkService.getUsedTags(), HttpStatus.OK);
    }

    @GetMapping(value ="{countryName}")
    public ResponseEntity findAreaTagsFromCountryName(@PathVariable("countryName") String country){
        return ResponseEntity.ok(networkService.findAreaTagsFromCountryName(country));
    }

}
