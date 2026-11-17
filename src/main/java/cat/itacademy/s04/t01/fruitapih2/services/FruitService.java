package cat.itacademy.s04.t01.fruitapih2.services;

import cat.itacademy.s04.t01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t01.fruitapih2.dto.FruitResponseDto;

public interface FruitService {
    FruitResponseDto createFruit (FruitRequestDto requestDto);
}
