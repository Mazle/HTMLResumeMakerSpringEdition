package hello;

import hello.Service.SeekerDescriptionProvider;
import hello.Service.impl.SeekerDescriptionService;
import hello.model.SeekerDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class GreetingController {
    private SeekerDescriptionProvider seekerDescriptionService;


    @GetMapping("/resume")
    public String getResume(@RequestParam(name="sources", required=false, defaultValue="") String sources, Model model) {
        SeekerDescription seekerDescription = seekerDescriptionService.getSeekerDescription(Arrays.asList(sources.split(",")));
        model.addAttribute("seekerDescription", seekerDescription);
        return "resume";
    }

    @Autowired
    public void setSeekerDescriptionService(SeekerDescriptionProvider seekerDescriptionService) {
        this.seekerDescriptionService = seekerDescriptionService;
    }
}
