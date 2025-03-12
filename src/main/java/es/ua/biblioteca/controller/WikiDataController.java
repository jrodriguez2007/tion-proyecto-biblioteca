package es.ua.biblioteca.controller;

import es.ua.biblioteca.service.WikidataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wikidata")
public class WikiDataController {

    @Autowired
    private WikidataService wikidataService;

    @GetMapping("/authorsbvmc")
    public String getAuthorsWikidata(Model model) {
        String json = wikidataService.getAuthors(10);
        model.addAttribute("json", json);
        return "external/wikidata";
    }

    @GetMapping("/authorsbylanguage")
    public String getAuthorsWikidata(@RequestParam(value = "language", defaultValue = "es") String language,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                                     Model model) {
        String json = wikidataService.getAuthorsByLanguage(limit, language);
        model.addAttribute("json", json);
        model.addAttribute("selectedLanguage", language);
        model.addAttribute("limit", limit);
        return "external/wikidata";
    }
}
