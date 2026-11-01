package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /* 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    // 회원 전체 조회

    private void validateDuplicateMember(Member member){
        //em.persist 할 때, id = pk
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id); // db에서 영속성 컨텍스트에 멤버를 올림
        member.setName(name); // 영속 상태의 멤버의 이름을 바꿈

        /*
        1. findOne(id) → DB에서 조회, 영속성 컨텍스트에 올림
        2. member.setName(name) → 영속 상태 엔티티의 값 변경
        3. 메서드 끝 → @Transactional AOP가 커밋 호출
        4. 커밋 직전 JPA가 변경 감지(dirty checking) → flush → UPDATE 쿼리 자동 실행

        더티 체킹이란
        - 영속성 컨텍스트의 최초 상태를 스냅샷 -> 이거랑 커밋 직전에 상태랑 비교해서 다르면 -> update 쿼리
        */

    }
}
