package org.nurma.hackathontemplate.controller;

import lombok.RequiredArgsConstructor;
import org.nurma.hackathontemplate.collection.Module;
import org.nurma.hackathontemplate.service.ModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/modules")
public class ModuleController {
    private final ModuleService moduleService;

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/pass")
    public Module passModule(
            @RequestParam final String moduleId,
            @RequestParam final Integer lessonIndex
    ) {
        return moduleService.passModule(moduleId, lessonIndex);
    }

    @PostMapping
    public void createModule(@RequestBody final Module module) {
        moduleService.createModule(module);
    }
}
