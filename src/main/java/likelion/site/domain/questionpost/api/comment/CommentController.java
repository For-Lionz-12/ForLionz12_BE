package likelion.site.domain.questionpost.api.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.dto.request.CommentRequestDto;
import likelion.site.domain.questionpost.dto.response.comment.CommentResponseDto;
import likelion.site.domain.questionpost.dto.response.comment.CommentResponseIdDto;
import likelion.site.domain.questionpost.service.comment.CommentService;
import likelion.site.domain.questionpost.service.post.QuestionPostService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.site.domain.questionpost.domain.success.CommentSuccess.COMMENT_CREATED_SUCCESS;
import static likelion.site.domain.questionpost.domain.success.CommentSuccess.GET_COMMENT_SUCCESS;

@Tag(name = "Comment", description = "댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final QuestionPostService questionPostService;

    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseIdDto>> createComment(@RequestBody CommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS,commentService.addComment(SecurityUtil.getCurrentMemberId(),request)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping
    public ResponseEntity<ApiResponse<CommentResponseIdDto>> deleteComment(@RequestParam Long commentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS,commentService.deleteComment(SecurityUtil.getCurrentMemberId(),commentId)));
    }

//    @Operation(summary = "댓글 수정")
//    @PutMapping
//    public ResponseEntity<ApiResponse<CommentResponseIdDto>> updateComment(@RequestBody UpdateCommentRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS,commentService.updateComment(SecurityUtil.getCurrentMemberId(),request)));
//    }

    @Operation(summary = "특정 질문글에 대한 모든 댓글 조회")
    @GetMapping("{questionPostId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getAllComments(@PathVariable Long questionPostId) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_COMMENT_SUCCESS,commentService.findCommentsByQuestionPost(questionPostId)));
    }
}
