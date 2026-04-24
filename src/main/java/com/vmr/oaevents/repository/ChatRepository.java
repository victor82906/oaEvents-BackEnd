package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE " +
           "(c.emisor.id = :emisorId AND c.receptor.id = :receptorId) OR " +
           "(c.emisor.id = :receptorId AND c.receptor.id = :emisorId) " +
           "ORDER BY c.fecha DESC")
    Page<Chat> findConversation(Long emisorId, Long receptorId, Pageable pageable);

}
