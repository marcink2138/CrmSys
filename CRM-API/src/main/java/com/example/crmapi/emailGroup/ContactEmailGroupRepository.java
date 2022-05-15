package com.example.crmapi.emailGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactEmailGroupRepository extends JpaRepository<ContactEmailGroup, Long> {
    @Modifying
    @Query("DELETE FROM ContactEmailGroup ceg WHERE ceg.contactId IN (?1) AND ceg.emailGroupId = ?2")
    void deleteAllByContactIdAndEmailGroupId(List<Long> contactId, Long emailGroupId);
}
