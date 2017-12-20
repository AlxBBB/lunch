package topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Menu;
import topjava.graduation.repository.menu.MenuRepository;

import static topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService{

    private final MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public Menu save(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id),id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id),id);
    }
}
