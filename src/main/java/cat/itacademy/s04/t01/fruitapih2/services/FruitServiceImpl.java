package cat.itacademy.s04.t01.fruitapih2.services;

import cat.itacademy.s04.t01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t01.fruitapih2.exception.FruitNotFoundException;
import cat.itacademy.s04.t01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t01.fruitapih2.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;

    public FruitServiceImpl (FruitRepository fruitRepository){
        this.fruitRepository = fruitRepository;
    }

    @Override
    public FruitResponseDto createFruit(FruitRequestDto requestDTO) {
        Fruit fruit = new Fruit();
        fruit.setName(requestDTO.name());
        fruit.setWeightInKg(requestDTO.weightInKg());

        Fruit saveFruit= fruitRepository.save(fruit);

        return new FruitResponseDto(saveFruit.getId(),saveFruit.getName(),saveFruit.getWeightInKg());
    }

    @Override
    public List<FruitResponseDto> getAllFruits () {
        List<Fruit> fruits = fruitRepository.findAll();

        return fruits.stream()
                .map(fruit -> new FruitResponseDto(
                        fruit.getId(),
                        fruit.getName(),
                        fruit.getWeightInKg()
                ))
                .toList();
    }

    @Override
    public FruitResponseDto getFruitById(Long id) {
        Fruit fruit= fruitRepository.findById(id)
                .orElseThrow(()-> new FruitNotFoundException("Fruit not found with id: "+ id));

        return new FruitResponseDto(fruit.getId(), fruit.getName(), fruit.getWeightInKg());
    }
}
