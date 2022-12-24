package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    
    
	//id값으로 질문 제목들 조회
//	public List<Question> getSubjectbyId(Integer id) {
//		List<Question> question = new ArrayList<>();
//		if(!question.isEmpty()) {
//			
//			return question = this.questionRepository.findAll(id);
//		}else
//			throw new DataNotFoundException("question not found");
//	}
    //페이징 검색
    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }
    
    //페이징
  	//이렇게 하면 데이터 전체를 조회하지 않고 해당 페이지의 데이터만 조회하도록 쿼리가 변경
    public Page<Question> getList(int page, String kw) {
    	List<Sort.Order> sorts = new ArrayList<>();
    	//Sort.Order 객체로 구성된 리스트에 Sort.Order 객체를 추가하고 Sort.by(소트리스트)로 소트 객체를 생성
    	
        sorts.add(Sort.Order.desc("createDate"));
        //게시물을 역순으로 조회하기 위해서는 PageRequest.of 메서드의 세번째 파라미터로 Sort 객체를 전달
        
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); 
        //PageRequest.of(page, 10)에서 page는 조회할 페이지의 번호이고 10은 한 페이지에 보여줄 게시물의 갯수를 의미
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);
//        return this.questionRepository.findAllByKeyword(kw, pageable);
    }
    
    //id값으로 질문 객체얻기
    public Question getQuestion(Integer id) {  
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {//Question 객체는 Optional 객체이기 때문에 위와 같이 isPresent 메서드로 해당 데이터가 존재하는지 검사하는 로직이 필요
            return question.get();
        } else {//만약 id 값에 해당하는 Question 데이터가 없을 경우에는 DataNotFoundException을 발생
            throw new DataNotFoundException("question not found");
        }
    }
    //optional 타입으로 question 객체 얻기
    public Optional<Question> getQuestionforOptional(Integer id){
    	Optional<Question> q = this.questionRepository.findById(id);
    	if(q.isPresent()) {
    		return q;
    	}else {
    		throw new DataNotFoundException("question not found");
    	}

    }
    
    //사용자 이름으로 questin 리스트 얻기
    public List<Question> getQuestionListOfUser(String username){
    	SiteUser user = this.userService.getUser(username);
    	return this.questionRepository.findAllByAuthor(user);
    }
    
    
    
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}