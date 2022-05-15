package com.example.crmapi.contact;

import com.example.crmapi.genericCrud.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends GenericRepository<Contact> {
    List<Contact> findAllByAccountGroupId(Long groupId);

    List<Contact> findAllByCreatedBy(Long userId);

    @Query("SELECT c FROM Contact c WHERE c.createdBy = ?1 OR c.accountGroupId = ?2")
    List<Contact> findContactEmailsByCreatedByAndGroupId(Long userId, Long groupId);

    Contact findByEmailAddress(String email);

    @Query("SELECT c1 FROM Contact c1, ContactEmailGroup ceg WHERE ceg.contactId = c1.id AND ceg.emailGroupId =?1")
    List<Contact> findContactEmailsByEmailGroupId(Long emailGroupId);

    Contact getByEmailAddress(String emailAddress);

    @Query("SELECT c FROM Contact c WHERE c.emailAddress IN (?1)")
    List<Contact> findAllByEmailAddresses(List<String> emails);

    @Query("SELECT c FROM Contact c WHERE c.id IN " +
            "(SELECT DISTINCT ceg.contactId FROM ContactEmailGroup ceg, EmailGroup eg " +
            "WHERE ceg.emailGroupId=eg.id AND eg.forecastSub = true )")
    List<Contact> findContactsWithForecastSubscription();
}
