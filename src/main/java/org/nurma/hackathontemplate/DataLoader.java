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

        Module.Question question1 = new Module.Question();
        question1.setQuestion("""
                Байлық дегеніміз не? Бесінші сыныпта «Байлық дегеніміз не?» атты ашық сабақ жүріп жатты. Оқушылар бір-бірімен әңгімелесіп, өз ойларын айту үшін дайындалып жатыр. Мұғалім үстел бетіндегі қағаздарға қарап, іске кірісті. Балалар, байлық дегеніміз не? Бірінші партада отырған Сәбит қолын көтерді.\s
                - Байлық дегеніміз көп акша, жаңа машина, деп ойланып қалды. Отыр. Сәбит. Тағы кім жауап береді? Келесі Алмас қол көтерді.\s
                -Байлыкка қора-қора кой, үйір-үйір жылқы және алты бөлмелі әдемі үй жатады. Мұғалім бұл жауаптарға көңілі толмағанын білдірді.
                -Қане, кім калай жауап береді? Иә. Сайлаубек, сен айта қойшы!\s
                -Байлық дегеніміз-кітап, білім,- деп жауап айтты.\s
                -Жарайды, отыра ғой. Жақсы жауап. Біраз уақыттан бері қол көтеріп отырған Сәулеге де сөз берілді.\s
                -Біздің үйде түрлі-түсті теледидар, бейнемагнитофон және жаңа үй жиһаздары көп. Байлық дегеніміз осы. Жақсы, отыра бер. Бұдан кейін Серік, Нұрболат, Құралай есімді оқушылар үйдегі
                байлықтарын ортаға салды. Оқушылардың берген жауаптары мұғалім көңілінен шықпады. Бір кезде оның көзі артқы партада үндемей өзімен-өзі отырған Аманжолға түсті.\s
                -Аманжол, сен неге үндемей отырсын? Қане, байлық дегеніміз не? Айта қойшы.\s
                -Апай, мен бай емеспін. Мен үшін ең үлкен байлық әкем мен анам, - деп жауап берді. Мұғалімге оқушының жауабы қатты ұнады.\s
                -Көрдіңдер ме, балалар, байлық дегеніміз тек ақша емес. Сендердің ең бірінші байлықтарың Аманжол айтқандай, ата-аналарың мен бауырларың, туысқандарың. Сондықтан әрқашан отбасыларың аман болсын. Міне, байлық деген осы. Бұл жауаптан кейін ешкім қол көтермеді.
                Оқушылар ойланып қалды.""");
        question1.setType(Module.QuestionType.READING);
        Module.Question question2 = new Module.Question();
        question2.setQuestion("Мәтінде жауабы бар сұрақты таңдаңыз.");
        question2.setOptions(List.of("Бірінші байлық дегеніміз не?", "Отбасы тәрбиесі қандай?", "Қалай бай болуға болады?", "Ашық сабақ қайда өтті?"));
        question2.setAnswer("Бірінші байлық дегеніміз не?");
        question2.setType(Module.QuestionType.MCQ);
        Module.Question question3 = new Module.Question();
        question3.setQuestion("Мәтінге сәйкес ақпаратты таңдаңыз.");
        question3.setOptions(List.of("Сайлаубек байлықты кітап оқу деп түсінеді.", "Балалардың байлық туралы түсініктері бірдей.", "Сәуле байлықты теледидар көру деп түсінеді.", "Балалардың байлық туралы түсініктері жоқ болып шықты."));
        question3.setAnswer("Сайлаубек байлықты кітап оқу деп түсінеді.");
        question3.setType(Module.QuestionType.MCQ);
        Module.Question question4 = new Module.Question();
        question4.setQuestion("Мәтіндегі басты тірек сөздерді табыңыз.");
        question4.setOptions(List.of("байлық, кітап, білім, жиһаз", "мұғалім, ашық сабақ, оқушы, байлық", "отбасы, байлық, теледидар, көзқарас", "байлық, ата-ана, ашық сабақ, бейнемагнитофон"));
        question4.setAnswer("мұғалім, ашық сабақ, оқушы, байлық");
        question4.setType(Module.QuestionType.MCQ);
        Module.Question question5 = new Module.Question();
        question5.setQuestion("Мәтін мазмұнына сәйкес жауапты табыңыз. Байлық туралы Аманжолдың ойы қандай?");
        question5.setOptions(List.of("Адамға керегі жиһаз, ақша.", "Байлық дегеніміз - кітап, білім.", "Өмірде ең басты байлық әке-шеше.", "Адам үшін бірінші байлық алты бөлмелі үй."));
        question5.setAnswer("Өмірде ең басты байлық әке-шеше.");
        question5.setType(Module.QuestionType.MCQ);
        Module.Question question6 = new Module.Question();
        question6.setQuestion("Мәтіндегі негізгі ойды анықтаңыз.");
        question6.setOptions(List.of("Аманжолдың ата-анасы.", "Отбасы басты байлық болып саналады.", "Байлық-денсаулық кепілі бола алады.", "Мектепте ашық сабақ өткен."));
        question6.setAnswer("Отбасы басты байлық болып саналады.");
        question6.setType(Module.QuestionType.MCQ);

        List<Module.Question> readingQuestions = List.of(question1, question2, question3, question4, question5, question6);

        Module.Question question7 = new Module.Question();
        question7.setQuestion("«Әдемі» сөзінің синонимін анықтаңыз");
        question7.setOptions(List.of("жаман", "үлкен", "кеш", "сұлу"));
        question7.setAnswer("сұлу");
        question7.setType(Module.QuestionType.MCQ);
        Module.Question question8 = new Module.Question();
        question8.setQuestion("Ұяңнан басталып,ұянға аяқталған сөзді табыңыз");
        question8.setOptions(List.of("тоғыз", "жүз", "бір", "сегіз"));
        question8.setAnswer("жүз");
        question8.setType(Module.QuestionType.MCQ);
        Module.Question question9 = new Module.Question();
        question9.setQuestion("Қатаң дауыссыз дыбыстардан тұратын сөзді анықтаңыз");
        question9.setOptions(List.of("сәлем", "тіл", "оқушы", "арман"));
        question9.setAnswer("сәлем");
        question9.setType(Module.QuestionType.MCQ);
        Module.Question question10 = new Module.Question();
        question10.setQuestion("Жұрнақ арқылы жасалған зат есімді анықтаңыз");
        question10.setOptions(List.of("үй", "достық", "арман", "көл"));
        question10.setAnswer("достық");
        question10.setType(Module.QuestionType.MCQ);
        Module.Question question11 = new Module.Question();
        question11.setQuestion("Синонимдерді табыңыз");
        question11.setOptions(List.of("кітап,парақ", "бет,жүз", "ғылым,ғалым", "білім,тәлімгер"));
        question11.setAnswer("білім,тәлімгер");
        question11.setType(Module.QuestionType.MCQ);

        List<Module.Question> grammarQuestions = List.of(question7, question8, question9, question10, question11);

        Module.Question question12 = new Module.Question();
        question12.setQuestion("Ғылым таппай мақтанба.\n" +
                "Орын таппай баптанба.\n" +
                "Құмарланып шаттанба.\n" +
                "Ойнап босқа күлуге.");
        question12.setType(Module.QuestionType.AUDIO);
        Module.Question question13 = new Module.Question();
        question13.setQuestion("""
                Ақ киімді, денелі, ақ сақалды,
                Соқыр мылқау танымас тірі жанды.
                Үсті-басы ақ қырау түсі суық,
                Басқан жері сықырлап келіп қалды.
                """);
        question13.setType(Module.QuestionType.AUDIO);
        Module.Question question14 = new Module.Question();
        question14.setQuestion("""
                Баяғы бір заманда бай мен кедей көрші өмір сүріпті. Олардың араздығы сондай, бірін-бірі көргісі келмейді екен. Байдың үйі кең, әдемі болыпты. Іші толған – алтын мен күміс.
                """);
        question14.setType(Module.QuestionType.AUDIO);

        List<Module.Question> listeningQuestions = List.of(question12, question13, question14);

        Module module1 = new Module();
        module1.setTitle("Бастапқы этап, 1 бөлім");

        Module.Lesson lesson1 = new Module.Lesson();
        lesson1.setTitle("Оқу және түсіну");
        lesson1.setQuestions(readingQuestions);
        Module.Lesson lesson2 = new Module.Lesson();
        lesson2.setTitle("Грамматика");
        lesson2.setQuestions(grammarQuestions);
        Module.Lesson lesson3 = new Module.Lesson();
        lesson3.setTitle("Тыңдау және сөйлеу");
        lesson3.setQuestions(listeningQuestions);

        List<Module.Lesson> lessons = List.of(lesson1, lesson2, lesson3);
        module1.setLessons(lessons);
        moduleService.createModule(module1);

        Module module2 = new Module();
        module2.setTitle("Орташа этап, 2 бөлім");
        module2.setLessons(lessons);
        moduleService.createModule(module2);

        Module module3 = new Module();
        module3.setTitle("Ілгері этап, 3 бөлім");
        module3.setLessons(lessons);
        moduleService.createModule(module3);
    }
}
