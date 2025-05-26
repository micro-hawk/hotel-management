package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.Item;
import com.spring.exception.ItemNotFoundException;
import com.spring.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	// add item
	public Item addItem(Item item) {
		return itemRepository.save(item);
	}

	// update item
	public Item updateItem(Item item, int id) throws ItemNotFoundException {
		Item oldItem = itemRepository.findById(id).orElse(null);
		if (oldItem != null) {
			oldItem.setName(item.getName());
			oldItem.setPrice(item.getPrice());
			oldItem.setQuantity(item.getQuantity());
			oldItem.setDescription(item.getDescription());
			return itemRepository.save(oldItem);
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}
	// delete item

	public Item deleteItem(int id) throws ItemNotFoundException {
		Item oldItem = itemRepository.findById(id).orElse(null);
		if (oldItem != null) {
			itemRepository.deleteById(id);
			return oldItem;
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}

	// view all items
	public List<Item> getItems() {
		return itemRepository.findAll();
	}

	// find by id
	public Item getItemById(int id) {
		return itemRepository.findById(id).orElse(null);
	}

}
