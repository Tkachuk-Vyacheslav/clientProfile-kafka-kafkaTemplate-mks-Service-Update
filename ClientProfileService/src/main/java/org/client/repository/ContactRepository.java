package org.client.repository;

import org.client.common.entity.ContactMedium;
import org.client.common.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<ContactMedium, String> {

    //  ищем клиента по uuid контактов
    @Query("from Individual as ind join fetch ind.contacts as cont where cont.uuid = :uuid")
    Individual findClientByContactuuid(@Param("uuid") String uuid);

    //  ищем ContactMedium по uuid email
    @Query("from ContactMedium as cont join fetch cont.emails as em where em.uuid = :uuid")
    ContactMedium findContactMediumByEmailId(@Param("uuid") String uuid);

}
