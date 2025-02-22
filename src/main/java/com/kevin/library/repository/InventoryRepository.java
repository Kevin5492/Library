package com.kevin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.library.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}
