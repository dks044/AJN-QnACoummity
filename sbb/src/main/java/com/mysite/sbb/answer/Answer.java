package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne //N:1 관계
    private Question question;
    //question 속성은 답변 엔티티에서 질문 엔티티를 참조하기 위해 추가했다. 
    //예를 들어 답변 객체(예:answer)를 통해 질문 객체의 제목을 알고 싶다면 
    //answer.getQuestion().getSubject()처럼 접근할 수 있다. 하지만 이렇게 속성만 추가하면 안되고 
    //질문 엔티티와 연결된 속성이라는 것을 명시적으로 표시해야 한다.
    //즉, 다음과 같이 question 속성에 @ManyToOne 애너테이션을 추가해야 한다.
    
    //작성자
    @ManyToOne //N:1 관계
    private SiteUser author;
    
    private LocalDateTime modifyDate;
    
    @ManyToMany
    Set<SiteUser> voter;
    
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;
    
    
}