package topjava.graduation.service;

import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Menu;

public interface MenuService {
    Menu save(Menu menu);

    void delete(int id) throws NotFoundException;

    Menu get(int id) throws NotFoundException;
}
