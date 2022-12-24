package com.mysite.sbb;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    
    
 // @Test
 // void testJpa() {
 // for (int i = 1; i <= 300; i++) {
 // String subject = String.format("테스트 데이터입니다:[%03d]", i);
 // String content = "내용무";
 // this.questionService.create(subject, content,null);
 // }
 // }
 // @Transactional
 // @Test
 //// void checkAuthor() {
 //// SiteUser user = this.userService.getUser("test3");
 ////
 //// System.out.println(user);
 //// Question q = new Question();
 //// q= this.questionService.create("수정테스트", "수정1", user);
 //// questionRepository.save(q);
 //// assertEquals("종윤입니다", q.getAuthor());
 //
 //
 //
 // }

 // @Test
 // void test() {
 // List<Answer> all = this.answerRepository.findAll();
 // System.out.println("저의 사이즈는 ="+all.size());
 //
 // }

// @Transactional
// @Test
// void test() {
// Optional<Question> question = this.questionRepository.findById(357);//357확실히잇음
// Optional<SiteUser> user = this.userRepository.findByusername("test3");
// for (int i = 1; i <= 150; i++) {
// String content = String.format("답변테스트 답변데이터입니다:[%03d]", i);
// this.answerService.create(question.get(), content, user.get());
// }
// }

//  @Test
//  void test() {
//  Optional<Question> question = this.questionRepository.findById(357);
//  System.out.println("저는 id357입니다=>"+question.get().getSubject());
// 
//  }
    
//    @Transactional
//    @Test
//    void test() {
//    	String sort = "voter";
//    	Question q = this.questionService.getQuestion(358);
//    	Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, sort));
//    	Page<Answer> ansList = this.answerRepository.findAllByQuestion(q, pageable);;
//    	System.out.println("==================TEST DATA==================");
//    	for(Answer item : ansList) {
//    		System.out.println(item.getContent());
//    	}
//    	System.out.println("==================TEST DATA==================");
//    }
    @Transactional
    @Test
    void test() {
    	Page<Answer> a = this.answerService.getAnswerList(358, 1, "voter");
    	for(Answer a1 : a) {
    		System.out.println(a1.getContent());
    	}
    	
    	
    }
    
 }
