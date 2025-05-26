package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.OrderedItem;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {

}