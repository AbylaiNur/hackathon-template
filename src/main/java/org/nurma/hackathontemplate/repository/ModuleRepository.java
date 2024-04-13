package org.nurma.hackathontemplate.repository;

import org.nurma.hackathontemplate.collection.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModuleRepository extends MongoRepository<Module, String> {
}
