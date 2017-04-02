package me.wonwoo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@RestController
public class FooController {

  @GetMapping("/")
  public Map<String, String> hello() {
    return Collections.singletonMap("message", "Hello world");
  }
}
