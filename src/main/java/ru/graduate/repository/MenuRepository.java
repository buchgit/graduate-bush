package ru.graduate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.graduate.model.Menu;

public interface MenuRepository extends JpaRepository <Menu,Integer>{
}
