package likelion.site.controller;

import likelion.site.domain.ChildComment;
import likelion.site.domain.Comment;
import likelion.site.domain.Member;
import likelion.site.service.ChildCommentService;
import likelion.site.service.CommentService;
import likelion.site.service.MemberService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/childcomment")
public class ChildCommentController {

    private final MemberService memberService;
    private final ChildCommentService childCommentService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ChildCommentResponseIdDto> createChildComment(@RequestBody ChildCommentRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        ChildComment childcomment = ChildComment.builder()
                .member(member)
                .comment(request.comment)
                .content(request.content)
                .build();
        Long id = childCommentService.addChildComment(childcomment);
        commentService.addChildComment(request.comment, childcomment);
        return ResponseEntity.ok().body(new ChildCommentResponseIdDto(childcomment));
    }

    @GetMapping
    public ResponseEntity<Result> getAllChildComments(@RequestParam Long commentId) {
        Comment comment = commentService.findById(commentId);
        List<ChildComment> childComments = childCommentService.findChildCommentsByComment(comment);
        List<ChildCommentResponseDto> collect = childComments.stream()
                .map(ChildCommentResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Data
    public static class ChildCommentResponseDto {
        Long id;
        Long memberId;
        String content;
        Comment comment;

        public ChildCommentResponseDto(ChildComment childComment) {
            this.id = childComment.getId();
            this.memberId = childComment.getMember().getId();
            this.content = childComment.getContent();
            this.comment = childComment.getComment();
        }
    }

    @Data
    public static class ChildCommentResponseIdDto {
        Long id;

        public ChildCommentResponseIdDto(ChildComment childComment) {
            this.id = childComment.getId();
        }
    }

    @Data
    public static class ChildCommentRequestDto {
        String content;
        Comment comment;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
