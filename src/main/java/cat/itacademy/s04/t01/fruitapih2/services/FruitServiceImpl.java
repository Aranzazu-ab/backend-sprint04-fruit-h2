package cat.itacademy.s04.t01.fruitapih2.services;

import cat.itacademy.s04.t01.fruitapih2.dto.FruitRequestDto;
import cat.itacademy.s04.t01.fruitapih2.dto.FruitResponseDto;
import cat.itacademy.s04.t01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t01.fruitapih2.repository.FruitRepository;
import org.springframework.stereotype.Service;

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
}
