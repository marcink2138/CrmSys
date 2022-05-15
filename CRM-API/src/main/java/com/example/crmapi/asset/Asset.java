package com.example.crmapi.asset;

import com.example.crmapi.contact.Contact;
import com.example.crmapi.genericCrud.BaseEntity;
import com.example.crmapi.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Asset")
@Table(name = "asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderedBy;
    private Long soldBy;
    private Long amount;
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soldBy", insertable = false, updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderedBy", insertable = false, updatable = false)
    private Contact contact;
}
