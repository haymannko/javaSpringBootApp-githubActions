


package com.example.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

@Controller
public class CalculatorController {

    @GetMapping("/")
    public String showCalculator(Model model) {
        model.addAttribute("expression", "");
        return "calculator";
    }

    @PostMapping("/")
    public String calculate(@RequestParam(required = false) String expression,
                            @RequestParam String button,
                            Model model) {
        if (expression == null) expression = "";

        if ("C".equals(button)) {
            expression = "";
        } else if ("=".equals(button)) {
            try {
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                Object result = engine.eval(expression);
                expression = result.toString();
            } catch (Exception e) {
                expression = "Error";
            }
        } else {
            expression += button;
        }

        model.addAttribute("expression", expression);
        return "calculator";
    }
}
