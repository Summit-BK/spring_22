package spring.web.dto;

import lombok.Builder;
import lombok.Getter;
import spring.domain.MemberEntity;
import spring.domain.Role;

import java.util.Map;

@Getter
public class MemberDto {

    private Map<String, Object> attribute;
    private String attributeKey;
    private String name;
    private String email;

    @Builder
    public MemberDto(Map<String, Object> attribute, String attributeKey, String name, String email) {
        this.attribute = attribute;
        this.attributeKey = attributeKey;
        this.name = name;
        this.email = email;
    }

    public static MemberDto of(String registrationId, String userNameAttributeName, Map<String, Object> attribute){

        return ofGoogle(userNameAttributeName, attribute);
    }

    public static MemberDto ofGoogle(String userNameAttributeName, Map<String,Object> attribute){
        return MemberDto.builder()
               .name((String) attribute.get("name"))
                .email((String) attribute.get("email"))
                .attribute(attribute)
                .attributeKey(userNameAttributeName).build();
    }

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();

    }
}
