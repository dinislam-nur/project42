package ru.innopolis.stc27.maslakov.enterprise.project42.services.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public Page<Food> getPageFoods(Integer page, Integer size) {
        Pageable foods = PageRequest.of(page, size, Sort.by("name"));
        return foodRepository.findAll(foods);
    }

}
