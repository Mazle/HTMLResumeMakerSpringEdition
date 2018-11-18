package hello.Service;

import hello.model.SeekerDescription;

import java.util.List;

public interface SeekerDescriptionProvider {
    SeekerDescription getSeekerDescription(List<String> sources);
}
