package com.korea.test;

import com.korea.test.member.Member;
import com.korea.test.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        Optional<Member> memberOptional = memberRepository.findByLoginId(username);

        if(memberOptional.isEmpty()) {
            throw new UsernameNotFoundException(username + "라는 로그인 아이디가 존재하지 않습니다.");
        }

        Member member = memberOptional.get();
        String loginId = member.getLoginId();
        String password = member.getPassword();

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        return new User(loginId, password, authorities);
    }
}
