package spring.domain;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column
    String email;

    @Column
    Role role;

    @Builder
    public MemberEntity(Long id, String name, String email, Role role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
    public String getRolekey(){
        return this.role.getKey();
    }


}
