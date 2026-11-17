package cat.itacademy.s04.t01.fruitapih2.repository;

import cat.itacademy.s04.t01.fruitapih2.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
}
