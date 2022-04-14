package com.banvien.fc.location.controller;

import com.banvien.fc.common.util.GoogleLocationUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {

    @RequestMapping("/location/autoComplete")
    public List<String> findSameNameLocation(@RequestParam(name = "address") String address,
                                             @RequestParam(name = "language", required = false, defaultValue = "vi") String language){
        return GoogleLocationUtil.getSameNameLocation(address, language);
    }
}
