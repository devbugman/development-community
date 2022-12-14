package me.snsservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import me.snsservice.article.domain.Article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private Long view;

    //Todo 따로 response객체를 만들거나 해야함
    private String nickname;
    //Todo 태그와 좋아요 기능추가
    private int likeCount;
    private List<String> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime createAt;

    @Builder
    private ArticleResponse(Long id, String title, String content, Long view, String nickname, int likeCount, List<String> tags, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.tags = tags;
        this.createAt = createAt;
    }

    public static ArticleResponse of(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .nickname(article.getMember().getNickname())
                .view(article.getViewCount())
                .likeCount(article.getLikes().size())
                .tags(article.getTagNames())
                .createAt(article.getCreateDate())
                .build();
    }
}
