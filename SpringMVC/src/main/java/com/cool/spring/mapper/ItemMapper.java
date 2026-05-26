package com.cool.spring.mapper;

import com.cool.spring.dao.entity.Item;
import com.cool.spring.dto.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        if (item == null) {
            return null;
        }

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();

    }

    public Item toEntity(ItemDto dto) {
        if (dto == null) {
            return null;
        }

        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());

        return item;
    }

    public List<ItemDto> toDtoList(List<Item> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Item> toEntityList(List<ItemDto> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(this::toEntity)
                .toList();
    }
}
