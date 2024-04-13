package org.nurma.hackathontemplate;

import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.service.ModuleService;
import org.nurma.hackathontemplate.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.nurma.hackathontemplate.collection.Module;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final MongoTemplate mongoTemplate;
    private final ModuleService moduleService;
    private final UserService userService;

    @Override
    public void run(final String... args) {
        mongoTemplate.getDb().drop();

        List<String> emails = List.of(
                "zhanibek@gmail.com",
                "ainur@gmail.com",
                "nursultan@gmail.com"
        );

        for (String email : emails) {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(email);
            createUserRequest.setPassword("123456");
            userService.createUser(createUserRequest);
        }

        List<Module.Question> questions = List.of(
                Module.Question.builder()
                        .question("Кто убил Кений?")
                        .options(
                                List.of(
                                        "Юта",
                                        "Гожо",
                                        "Итадори",
                                        "Сукуна"
                                )
                        )
                        .answer(0)
                        .build(),
                Module.Question.builder()
                        .question("Кого самый сильнейший?")
                        .options(
                                List.of(
                                        "Жого",
                                        "Гожо",
                                        "ГёйГёй",
                                        "Сукуна"
                                )
                        )
                        .answer(2)
                        .build()
        );

        List<Module.Lesson> lessons = List.of(
                Module.Lesson.builder()
                        .title("Lesson 1")
                        .questions(questions)
                        .passedStudentIds(List.of())
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 2")
                        .questions(questions)
                        .passedStudentIds(List.of())
                        .build()
        );

        Module module = Module.builder()
                .title("Module 1")
                .lessons(lessons)
                .build();
        moduleService.createModule(module);
    }
}
