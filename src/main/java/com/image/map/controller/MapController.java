package com.image.map.controller;

import com.image.map.domain.EarthPornEntity;
import com.image.map.service.EarthPornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/map")
@Controller
public class MapController {

    @Autowired
    EarthPornService earthPornService;

    @GetMapping({ "/week" })
    public String hello(Model model)throws Exception {

        EarthPornEntity earthPornEntity = earthPornService.getImages("WEEK");

        model.addAttribute("message", earthPornEntity.getJson());
        return "Map";
    }

    @GetMapping({ "/month" })
    public String month(Model model)throws Exception {

        EarthPornEntity earthPornEntity = earthPornService.getImages("MONTH");

        model.addAttribute("message", earthPornEntity.getJson());
        return "Map";
    }

    @GetMapping({ "/year" })
    public String year(Model model)throws Exception {

        EarthPornEntity earthPornEntity = earthPornService.getImages("YEAR");

        model.addAttribute("message", earthPornEntity.getJson());
        return "Map";
    }

    @GetMapping({ "/all" })
    public String all(Model model)throws Exception {

        EarthPornEntity earthPornEntity = earthPornService.getImages("ALL");

        model.addAttribute("message", earthPornEntity.getJson());
        return "Map";
    }

    @GetMapping({ "/day" })
    public String day(Model model)throws Exception {

        EarthPornEntity earthPornEntity = earthPornService.getImages("DAY");

        model.addAttribute("message", earthPornEntity.getJson());
        return "Map";
    }


}
