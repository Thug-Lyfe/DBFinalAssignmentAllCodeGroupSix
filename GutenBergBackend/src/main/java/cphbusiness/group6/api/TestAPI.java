package cphbusiness.group6.api;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestAPI
{
    Gson gson = new Gson();


    @RequestMapping(value = "/tester/{someValue}", method = RequestMethod.GET)
    public String tester(@PathVariable("someValue") String userId)
    {

        return gson.toJson("hello " + userId);
    }

    @RequestMapping(value = "/teste/list", method = RequestMethod.GET)
    public String tester2()
    {
        ArrayList<String> list = new ArrayList<String>();

        list.add("a");
        list.add("b");
        list.add("c");

        return gson.toJson("books:" + list);

    }
}
