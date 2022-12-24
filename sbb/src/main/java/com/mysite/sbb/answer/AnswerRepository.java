package com.mysite.sbb.answer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
	//질문별 답변리스트 페이징
    Page<Answer> findAllByQuestion(Question question,Pageable pageable);
    
    List<Answer> findAllByAuthor(SiteUser author);
    
}