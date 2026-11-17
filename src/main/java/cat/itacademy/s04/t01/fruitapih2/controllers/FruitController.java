package cat.itacademy.s04.t01.fruitapih2.controllers;

import cat.itacademy.s04.t01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t01.fruitapih2.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FruitController {
    private final FruitService fruitService;

    public FruitController (FruitService fruitService){
        this.fruitService = fruitService;
    }

    @PostMapping("/fruits")
    public ResponseEntity<FruitResponseDto> createFruit (@Valid @RequestBody FruitRequestDto requestDto){
        FruitResponseDto responseDto = fruitService.createFruit(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
