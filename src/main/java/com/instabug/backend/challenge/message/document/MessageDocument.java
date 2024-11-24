package com.instabug.backend.challenge.message.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "messages")
@Getter
@Setter
public class MessageDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long chatNumber;

    @Field(type = FieldType.Keyword)
    private String applicationToken;

    @Field(type = FieldType.Long)
    private Long messageNumber;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String body;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    private Instant createdAt;
}
