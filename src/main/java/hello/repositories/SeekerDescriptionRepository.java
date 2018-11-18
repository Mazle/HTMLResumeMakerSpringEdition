package hello.repositories;

import hello.model.SeekerDescription;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface SeekerDescriptionRepository {
    public SeekerDescription getSeekerDescription(List<String> sources);
    final String[] KEYS = {"FIO","BirthDate","TelNumber","E-Mail","Target","Experience","Education","AdditionalEducation",
            "Skills","CodeExample","ImageSrc"};

}
