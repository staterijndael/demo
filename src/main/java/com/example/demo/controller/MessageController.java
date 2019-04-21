package com.example.demo.controller;

import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private List<Map<String,String>> messages = new ArrayList<Map<String,String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "First message"); }});
    }};
    private Map<String,String> getMessage(String id)
    {
        return messages.stream().filter(messages -> messages.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }
    @GetMapping
    public List<Map<String,String>> list()
    {
        return messages;
    }
    @GetMapping("{id}")
    public Map<String,String> getOne(@PathVariable String id)
    {
        return getMessage(id);
    }
    @PostMapping
    public List<Map<String,String>> create(@RequestBody Map<String,String> message)
    {
        if (messages.size() != 0) {
            message.put("id", String.valueOf(Integer.parseInt(messages.get(messages.size() - 1).get("id")) + 1));
        }
        else{message.put("id", "1");}

        messages.add(message);
        return messages;
    }
    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id)
    {
        Map<String,String> message = getMessage(id);

        messages.remove(message);
    }
}
