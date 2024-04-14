package org.nurma.hackathontemplate.collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "modules")
@EqualsAndHashCode(callSuper = true)
public class Module extends BaseCollection {
    @Id
    private String id;
    private String title;
    private List<Lesson> lessons = new ArrayList<>();

    @Data
    public static class Lesson {
        private String title;
        private List<Question> questions = new ArrayList<>();
        private List<String> passedStudentIds = new ArrayList<>();
    }

    @Data
    public static class Question {
        private String question;
        private List<String> options  = new ArrayList<>();
        private String answer;
        private QuestionType type;
    }

    public enum QuestionType {
        MCQ, MATCH, READING, AUDIO,
    }
}



