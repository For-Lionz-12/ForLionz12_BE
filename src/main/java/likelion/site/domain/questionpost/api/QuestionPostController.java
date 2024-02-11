package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.domain.success.QuestionPostSuccess;
import likelion.site.domain.questionpost.dto.request.QuestionPostRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostIdResponseDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.service.QuestionPostService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static likelion.site.domain.questionpost.domain.success.QuestionPostSuccess.*;

@Tag(name = "QuestionPost", description = "질문글(Q&A)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final MemberService memberService;

    @Operation(summary = "질문글 생성", description = "STAFF는 불가능합니다.")
    @PostMapping
    public ApiResponse<QuestionPostIdResponseDto> createQuestionPost(@RequestBody QuestionPostRequestDto request) {
        return ApiResponse.createSuccess(QUESTION_POST_CREATED_SUCCESS,questionPostService.addQuestionPost(SecurityUtil.getCurrentMemberId(),request));
    }

    @Operation(summary = "모든 질문글 조회")
    @GetMapping("/all")
    public ApiResponse<List<QuestionPostResponseDto>> findAllQuestionPosts() {
        return ApiResponse.createSuccess(GET_QUESTION_POST_SUCCESS,questionPostService.findAllQuestionPosts());
    }

    @Operation(summary = "id를 통해 질문글 상세 조회")
    @GetMapping
    public ApiResponse<QuestionPostResponseDto> getQuestionPostDetail(@RequestParam Long id) {
        return ApiResponse.createSuccess(GET_QUESTION_POST_SUCCESS,questionPostService.findQuestionPostById(id));
    }

    @Operation(summary = "특정 id의 질문글 수정", description = "접속 중인 사용자 본인의 글만 수정할 수 있습니다.")
    @PutMapping("{id}")
    public ApiResponse<QuestionPostIdResponseDto> updateQuestionPost(@PathVariable("id") Long id, @RequestBody QuestionPostRequestDto request) {
        return ApiResponse.createSuccess(QUESTION_POST_UPDATED_SUCCESS,questionPostService.update(id, SecurityUtil.getCurrentMemberId(), request));
    }

    @Operation(summary = "특정 id의 질문글 삭제", description = "접속 중인 사용자 본인의 글만 삭제할 수 있습니다.")
    @DeleteMapping("{id}")
    public ApiResponse<QuestionPostIdResponseDto> deleteQuestionPost(@PathVariable("id") Long id) {
        return ApiResponse.createSuccess(DELETE_QUESTION_POST_SUCCESS,questionPostService.delete(id));
    }
}
