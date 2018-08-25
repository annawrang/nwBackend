package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.AreaTagAlternative;
import se.netwomen.NetWomenBackend.model.data.network.tag.alternative.CountryTagAlternative;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative.AreaTagAlternativeDTO;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative.CountryTagAlternativeDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class AlternativeParser {

    public static CountryTagAlternativeDTO countryTagAlternativeToNewEntity(CountryTagAlternative countryTag){ // ny tag
        return new CountryTagAlternativeDTO(null, countryTag.getName().trim(), null);
    }

    public static CountryTagAlternative entityToExistingCountryTagAlternative(CountryTagAlternativeDTO countryTagDTO, List<AreaTagAlternative> areaTagAlternatives){
        return new CountryTagAlternative(countryTagDTO.getName(), areaTagAlternatives);
    }

    public static AreaTagAlternative entityToExistingAreaTagAlternative(AreaTagAlternativeDTO areaTagAlternativeDTO){
        return new AreaTagAlternative (areaTagAlternativeDTO.getName());
    }

    public static List<CountryTagAlternative> parseCountryTagAlternativeDTOs(List<CountryTagAlternativeDTO> countryTagAlternatives){
        return countryTagAlternatives
                .stream()
                .map(country ->
                        AlternativeParser.entityToExistingCountryTagAlternative(country, parseAreaTagAlternativeDTOs(country.getAreaTagAlternativeDTOs())))
                .collect(Collectors.toList());
    }

    private static List<AreaTagAlternative> parseAreaTagAlternativeDTOs(Set<AreaTagAlternativeDTO> areaTagAlternativeDTOs){
        return  areaTagAlternativeDTOs
                .stream()
                .map(city ->
                        AlternativeParser.entityToExistingAreaTagAlternative(city))
                .collect(Collectors.toList());
    }
}
