package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import likelion.site.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class QuestionPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Column(length = 50000)
    private String content;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "questionPost")
    private List<Comment> comments;

    @Builder
    public QuestionPost(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void updateQuestionPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}