package likelion.site.domain.questionpost.dto.request;

import lombok.Getter;

@Getter
public class ChildTagRequestDto {

    String name;
    Long parentTagId;
}