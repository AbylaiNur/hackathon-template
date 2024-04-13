package org.nurma.hackathontemplate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nurma.hackathontemplate.collection.Module;
import org.nurma.hackathontemplate.collection.User;
import org.nurma.hackathontemplate.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final AuthService authService;

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module passModule(final String moduleId, final Integer lessonIndex) {
        User user = authService.getCurrentUserEntity();

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResolutionException("Module not found"));

        List<Module.Lesson> lessons = module.getLessons();

        lessons.get(lessonIndex).getPassedStudentIds().add(user.getId());

        return moduleRepository.save(module);
    }

    public void createModule(final Module module) {
        moduleRepository.save(module);
    }
}
