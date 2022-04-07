package com.company.controller;

import com.company.dto.CardDTO;
import com.company.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/card")
public class CardController {
    // final Logger log= LoggerFactory.getLogger(CardController.class);

    @Autowired
    private CardService cardService;

    @PostMapping("/create")
    public ResponseEntity<CardDTO> create(@RequestBody CardDTO dto) {
        log.info("New Card Added {}", dto);
        CardDTO response = cardService.create(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("card_number") String number) {
        log.info("Card is deleting by number:{}", number);
        cardService.delete(number);
        return ResponseEntity.ok("Deleted!!!");
    }

    @PutMapping("/increaseBalance/{cardNumber}")
    public ResponseEntity<String> increaseBalance(@PathVariable("cardNumber")String number,
                                                  @RequestParam("amount") Long amount){
        cardService.increaseBalance(number,amount);
        return ResponseEntity.ok("successful");
    }

    @GetMapping("/get_by_number/{number}")
    public ResponseEntity<CardDTO> getByNumber(@PathVariable("number") String number) {
        log.info("Get card by number:{}", number);
        CardDTO cardDTO = cardService.getByNumber(number);
        return ResponseEntity.ok(cardDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CardDTO>> getAll(){
        return ResponseEntity.ok(cardService.getAll());
    }


//------------------------------------------------------------------------------------------------------
    @GetMapping("/test/method")
    public ResponseEntity<String> test_str(@RequestParam("size") int size,
                                           @RequestParam("page") int page) {
        log.info("New Request Param: size:{},page:{}",size,page);
        return ResponseEntity.ok("Test method");
    }
    @GetMapping( value = "/test/header",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test_header() {
        return ResponseEntity.ok("Test method");
    }
//------------------------------------------------------------------------------------------------------
}
