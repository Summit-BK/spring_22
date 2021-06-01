package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import spring.domain.MemberEntity;
import spring.domain.MemberRepository;
import spring.web.dto.MemberDto;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Oauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        MemberDto memberDto = MemberDto.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());
        MemberEntity memberEntity = saveorupdate(memberDto);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(memberEntity.getRolekey()))
                ,memberDto.getAttribute()
                ,memberDto.getAttributeKey()

        );


    }

    private MemberEntity saveorupdate(MemberDto memberDto){
        return memberRepository.save(memberDto.toEntity());
    }


}
