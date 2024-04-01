package org.nurma.hackathontemplate.collection;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
public class BaseCollection {
    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @CreatedBy
    @Field("created_by")
    private ObjectId createdBy;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;

    @LastModifiedBy
    @Field("updated_by")
    private ObjectId updatedBy;
}
