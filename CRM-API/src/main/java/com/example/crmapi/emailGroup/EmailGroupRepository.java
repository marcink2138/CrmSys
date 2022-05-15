package com.example.crmapi.emailGroup;

import com.example.crmapi.genericCrud.GenericRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailGroupRepository extends GenericRepository<EmailGroup> {
    @Query("SELECT eg FROM EmailGroup eg WHERE eg.userId = ?1")
    List<EmailGroup> findEmailGroupByUserId(Long userId);

    @Modifying
    @Query(value = "INSERT INTO contact_email_group", nativeQuery = true)
    void addContactsToEmailGroup(Long emailGroupId, List<String> emails);

    @Modifying
    @Query(value = "UPDATE EmailGroup eg SET eg.forecastSub = ?2 WHERE eg.emailGroupName IN ?1")
    void setGroupsForecastSub(List<String> groups, Boolean addOrDelete);

}
