package xyz.lannt.presentation.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/market")
public class MarketController {

  @RequestMapping(method = RequestMethod.GET)
  public String get() {
    return "html/market";
  }

  @MessageMapping("/timestamp")
  @SendTo("/topic/market")
  public ResponseEntity<?> getTimestamp() throws Exception {
    // Thread.sleep(1000); // simulated delay
    return ResponseEntity.ok(LocalDateTime.now().toString());
  }
}
