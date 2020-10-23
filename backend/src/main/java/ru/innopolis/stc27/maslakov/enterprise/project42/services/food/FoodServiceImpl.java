package ru.innopolis.stc27.maslakov.enterprise.project42.services.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.FoodsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public Page<Food> getPageFoods(Integer page, Integer size, FoodCategory foodCategory) {
        Pageable foods = PageRequest.of(page, size, Sort.by("id"));
        return foodRepository.findAllByFoodCategory(foodCategory, foods);
    }

    @Override
    public void createOrUpdateFood(FoodsDTO foodsDTO) {
        Food food = Food.builder()
                .id(foodsDTO.getId())
                .name(foodsDTO.getName())
                .picture(foodsDTO.getPicture())
                .foodCategory(foodsDTO.getCategory())
                .price(foodsDTO.getPrice())
                .build();
        foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long foodId) {
        foodRepository.deleteById(foodId);
    }

    @Override
    public FoodsDTO getFood(Long foodId) {
        return DTOConverter.convertToDTO(
                foodRepository.findById(foodId)
                        .orElseThrow(() -> new IllegalStateException("Блюда с id " + foodId + " в бд не существует")));
    }

}
