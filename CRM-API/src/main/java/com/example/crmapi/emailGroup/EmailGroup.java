package com.example.crmapi.emailGroup;

import com.example.crmapi.genericCrud.BaseEntity;
import com.example.crmapi.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "email_group")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailGroupName;
    private Long userId;
    private Boolean forecastSub;
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    public EmailGroup(String emailGroupName, Long userId){
        this.emailGroupName = emailGroupName;
        this.userId = userId;
    }

}
