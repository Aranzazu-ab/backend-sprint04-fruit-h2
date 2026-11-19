package cat.itacademy.s04.t01.fruitapih2.controllers;

import cat.itacademy.s04.t01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t01.fruitapih2.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruits")
public class FruitController {
    private final FruitService fruitService;

    public FruitController (FruitService fruitService){
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitResponseDto> createFruit (@Valid @RequestBody FruitRequestDto requestDto){
        FruitResponseDto responseDto = fruitService.createFruit(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<FruitResponseDto>> getAllFruits(){
        List<FruitResponseDto> fruits = fruitService.getAllFruits();
        return ResponseEntity.ok(fruits);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<FruitResponseDto> getFruitById(@PathVariable Long id){
        FruitResponseDto fruit = fruitService.getFruitById(id);

        return ResponseEntity.ok(fruit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> updateFruit(
            @PathVariable Long id,
            @Valid @RequestBody FruitRequestDto requestDto) {

        FruitResponseDto updatedFruit = fruitService.updateFruit(id, requestDto);
        return ResponseEntity.ok(updatedFruit);
    }

}
