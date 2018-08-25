package se.netwomen.NetWomenBackend.service.Parsers;

import se.netwomen.NetWomenBackend.model.data.PostComplete.UserMinimum;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Post.PostLikeDTO;

public final class PostLikeParser {


    public static UserMinimum dtoToUserMinimum(PostLikeDTO postLikeDTO) {
        return UserParser.toUserMinimum(postLikeDTO.getUser());
    }
}
