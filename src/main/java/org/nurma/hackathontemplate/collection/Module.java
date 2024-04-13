package org.nurma.hackathontemplate.collection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "modules")
@EqualsAndHashCode(callSuper = true)
public class Module extends BaseCollection {
    @Id
    @Setter(AccessLevel.NONE)
    private String id;
    private String title;
    private List<Lesson> lessons;

    @Data
    @Builder
    public static class Lesson {
        private String title;
        private List<Question> questions;
        private List<String> passedStudentIds;
    }

    @Data
    @Builder
    public static class Question {
        private String question;
        private List<String> options;
        private int answer;
    }
}



