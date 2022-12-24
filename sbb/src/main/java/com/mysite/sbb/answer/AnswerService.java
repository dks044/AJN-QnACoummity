package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }
    //id값으로 답변객체 조회
    public Answer getAnswer(Integer id) { //Optional (null값 처리 유연하게 하는 용도)
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else { //없을경우 예외 발생
            throw new DataNotFoundException("answer not found");
        }
    }
    
    //사용자명으로 답변리스트 조회
    public List<Answer> getAnswerListForUser(String username){
    	SiteUser user = this.userService.getUser(username);
    	return this.answerRepository.findAllByAuthor(user);
    }
    
    
    
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    
    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
    
    //답변리스트 페이징
    public Page<Answer> getAnswerList(Integer id,int page,String sort){
        Question question = this.questionService.getQuestion(id);
//      List<Sort.Order> sort1 = new ArrayList<>();//최신순
//      sort1.add(Sort.Order.desc("createDate")); 

        //@RequestParam sort로 읽어오면 적용
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,sort));
        return this.answerRepository.findAllByQuestion(question, pageable);
    }
}