package com.instabug.backend.challenge.message.elastic;


import com.instabug.backend.challenge.message.document.MessageDocument;
import com.instabug.backend.challenge.message.dto.MessageSearchResponse;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageSearchRepository extends ElasticsearchRepository<MessageDocument, Long> {

    List<MessageDocument> findByApplicationTokenAndChatNumberAndBodyContaining(String applicationToken, Long chatNumber, String keyword);

    @Query("{ \"bool\": { \"must\": [ " +
            "{ \"term\": { \"applicationToken\": \"?0\" } }, " +
            "{ \"term\": { \"chatNumber\": \"?1\" } }, " +
            "{ \"term\": { \"messageNumber\": \"?2\" } } ] } }")
    MessageDocument findByApplicationTokenAndChatNumberAndMessageNumber(String applicationToken, Long chatNumber, Long messageNumber);

}
