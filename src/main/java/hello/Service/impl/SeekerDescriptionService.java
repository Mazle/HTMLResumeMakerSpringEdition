package hello.Service.impl;

import hello.Service.SeekerDescriptionProvider;
import hello.model.SeekerDescription;
import hello.repositories.SeekerDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeekerDescriptionService implements SeekerDescriptionProvider {

    @Autowired
    SeekerDescriptionRepository repository;

    @Override
    public SeekerDescription getSeekerDescription(List<String> sources) {
        return repository.getSeekerDescription(sources);
    }
}
