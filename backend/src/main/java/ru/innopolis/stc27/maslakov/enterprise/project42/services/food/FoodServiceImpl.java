package ru.innopolis.stc27.maslakov.enterprise.project42.services.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.FoodDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public Page<Food> getPageFoods(Integer page, Integer size, FoodCategory foodCategory) {
        Pageable foods = PageRequest.of(page, size, Sort.by("id"));
        return foodRepository.findAllByFoodCategory(foodCategory, foods);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_CHIEF', 'ROLE_ADMIN')")
    public void createOrUpdateFood(FoodDTO foodDTO) {
        Food food = Food.builder()
                .id(foodDTO.getId())
                .name(foodDTO.getName())
                .picture(foodDTO.getPicture())
                .foodCategory(foodDTO.getCategory())
                .price(foodDTO.getPrice())
                .build();
        foodRepository.save(food);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_CHIEF', 'ROLE_ADMIN')")
    public void deleteFood(Long foodId) {
        foodRepository.deleteById(foodId);
    }

    @Override
    public FoodDTO getFood(Long foodId) {
        return DTOConverter.convertToDTO(
                foodRepository.findById(foodId)
                        .orElseThrow(() -> new IllegalStateException("Блюда с id " + foodId + " в бд не существует")));
    }
}
