package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionTagMapRequestDto {

    Long questionPostId;
    List<Long> childTagId;

    public QuestionTagMap toEntity(QuestionPost questionPost, ChildTag childTag) {
        return QuestionTagMap.builder()
                .questionPost(questionPost)
                .childTag(childTag)
                .build();
    }
}
