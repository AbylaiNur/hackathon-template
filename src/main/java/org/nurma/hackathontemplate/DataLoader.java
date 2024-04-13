package org.nurma.hackathontemplate;

import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.collection.Module;
import org.nurma.hackathontemplate.dto.request.CreateUserRequest;
import org.nurma.hackathontemplate.service.ModuleService;
import org.nurma.hackathontemplate.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

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
                        .question("""
                                Hi Samia,
                                                               \s
                                Just a quick email to say that sounds like a great idea. Saturday is better for me because I'm meeting my parents on Sunday. So if that's still good for you, why don't you come here? Then you can see the new flat and all the work we've done on the kitchen since we moved in. We can eat at home and then go for a walk in the afternoon. It's going to be so good to catch up finally. I want to hear all about your new job!
                                                               \s
                                Our address is 52 Charles Road, but it's a bit difficult to find because the house numbers are really strange here. If you turn left at the post office and keep going past the big white house on Charles Road, there's a small side street behind it with the houses 50–56 in. Don't ask me why the side street doesn't have a different name! But call me if you get lost and I'll come and get you.
                                                               \s
                                Let me know if there's anything you do/don't like to eat. Really looking forward to seeing you!
                                                               \s
                                See you soon!
                                                               \s
                                Gregor"""
                        )
                        .type(Module.QuestionType.READING)
                        .build(),
                Module.Question.builder()
                        .question("What day is better for Gregor?")
                        .options(List.of("Monday", "Tuesday", "Saturday", "Sunday"))
                        .answer("Saturday")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("Where is Gregor's house?")
                        .options(List.of("52 Charles Road", "50 Charles Road", "56 Charles Road", "54 Charles Road"))
                        .answer("52 Charles Road")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("What will they do after eating?")
                        .options(List.of("Go to the cinema", "Go for a walk", "Go to the park", "Go to the beach"))
                        .answer("Go for a walk")
                        .type(Module.QuestionType.MCQ)
                        .build()
        );

        List<Module.Question> questions2 = List.of(
                Module.Question.builder()
                        .question("""
                                                                What can I take on the plane as hand luggage?
                                Please note that passengers can only take ONE suitcase onto the plane. It must be no bigger than 55cm x 22cm x 35cm and weigh no more than 10kg.

                                You can also take one small laptop bag or handbag that can fit under the seat in front of you. If you have two bags, their total weight cannot be more than 10kg. If your bag is too big or too heavy, you will not be allowed to take it onto the plane. Staff will put it in the hold for you and you will have to pay extra.

                                Please make sure mobile phones and other devices are fully charged so that security staff can check them.

                                Liquids in bottles bigger than 100ml are allowed on board if you buy them in the airport shops after you've passed security. 

                                We hope you enjoy your flight!""")
                        .type(Module.QuestionType.READING)
                        .build(),
                Module.Question.builder()
                        .question("How many bags can you take on the plane?")
                        .options(List.of("One", "Two", "Three", "Four"))
                        .answer("One")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("What is the maximum weight of the suitcase?")
                        .options(List.of("5kg", "10kg", "15kg", "20kg"))
                        .answer("10kg")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("What will happen if your bag is too big or too heavy?")
                        .options(List.of("You will not be allowed to take it onto the plane", "You will have to pay extra", "You will be allowed to take it onto the plane", "You will have to leave it at the airport"))
                        .answer("You will not be allowed to take it onto the plane")
                        .type(Module.QuestionType.MCQ)
                        .build()
        );

        List<Module.Lesson> lessons = List.of(
                Module.Lesson.builder()
                        .title("Lesson 1")
                        .questions(questions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 2")
                        .questions(questions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 3")
                        .questions(questions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 4")
                        .questions(questions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 5")
                        .questions(questions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 6")
                        .questions(questions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 7")
                        .questions(questions)
                        .build()
        );

        Module readingModule = Module.builder()
                .title("Reading Module")
                .lessons(lessons)
                .build();

        moduleService.createModule(readingModule);

        // GRAMMAR MODULE
        List<Module.Question> grammarQuestions = List.of(
                Module.Question.builder()
                        .question("I _______ to the cinema last night.")
                        .options(List.of("go", "went", "goes", "going"))
                        .answer("went")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("I am doing my homework now")
                        .answer("Я делаю свою домашнюю работу сейчас")
                        .type(Module.QuestionType.MATCH)
                        .build(),
                Module.Question.builder()
                        .question("She _______ a book now.")
                        .options(List.of("read", "reads", "is reading", "reading"))
                        .answer("is reading")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("He goes to the gym every day")
                        .answer("Он ходит в спортзал каждый день")
                        .type(Module.QuestionType.MATCH)
                        .build()
        );

        List<Module.Question> grammarQuestions2 = List.of(
                Module.Question.builder()
                        .question("I go to the cinema")
                        .answer("Я иду в кино")
                        .type(Module.QuestionType.MATCH)
                        .build(),
                Module.Question.builder()
                        .question("I _______ my homework now.")
                        .options(List.of("do", "does", "am doing", "doing"))
                        .answer("am doing")
                        .type(Module.QuestionType.MCQ)
                        .build(),
                Module.Question.builder()
                        .question("She is reading a book now")
                        .answer("Она читает книгу сейчас")
                        .type(Module.QuestionType.MATCH)
                        .build(),
                Module.Question.builder()
                        .question("He _______ to the gym every day.")
                        .options(List.of("go", "goes", "went", "going"))
                        .answer("goes")
                        .type(Module.QuestionType.MCQ)
                        .build()
        );

        List<Module.Lesson> grammarLessons = List.of(
                Module.Lesson.builder()
                        .title("Lesson 1")
                        .questions(grammarQuestions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 2")
                        .questions(grammarQuestions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 3")
                        .questions(grammarQuestions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 4")
                        .questions(grammarQuestions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 5")
                        .questions(grammarQuestions)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 6")
                        .questions(grammarQuestions2)
                        .build(),
                Module.Lesson.builder()
                        .title("Lesson 7")
                        .questions(grammarQuestions)
                        .build()
        );

        Module grammarModule = Module.builder()
                .title("Grammar Module")
                .lessons(grammarLessons)
                .build();

        moduleService.createModule(grammarModule);
    }
}
