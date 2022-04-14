package com.banvien.fc.promotion.utils.rule;

import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.dto.rules.RuleInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.awt.Mutex;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RuleProcessor {
    private Mutex mutex;
    private ScriptEngine engine;
    private static RuleProcessor instance = new RuleProcessor();

    public static RuleProcessor getInstance() {
        return instance;
    }

    public RuleProcessor() {
        try {
            mutex = new Mutex();
            engine = (new ScriptEngineManager()).getEngineByName("JavaScript");

            engine.eval(new InputStreamReader(RuleBuilder.class.getClassLoader().getResourceAsStream("logic.js")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String execute(String rule, String data) {
        mutex.lock();
        String rs = "";
        try {
            engine.eval("var rule = " + rule);
            engine.eval("var data = " + data);
            engine.eval("var result = JSON.stringify(jsonLogic.apply(rule,data))");
            rs = (String) engine.get("result");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mutex.unlock();
            return rs;
        }
    }

    public DiscountDTO execute(String rule, RuleInputDTO dto) {
        DiscountDTO rs = null;
        try {
            ObjectMapper oMapper = new ObjectMapper();
//            String test = oMapper.writeValueAsString(dto);
            String data = execute(rule, oMapper.writeValueAsString(dto));
            rs = oMapper.readValue(data, DiscountDTO.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

}
