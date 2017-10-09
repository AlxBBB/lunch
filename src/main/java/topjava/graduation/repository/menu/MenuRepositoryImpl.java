package topjava.graduation.repository.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import topjava.graduation.model.Menu;

@Repository
public class MenuRepositoryImpl implements MenuRepository{

    private  final CrudMenuRepository crudMenuRepository;

    @Autowired
    public MenuRepositoryImpl(CrudMenuRepository crudMenuRepository) {
        this.crudMenuRepository = crudMenuRepository;
    }

    @Override
    public Menu save(Menu menu) {
        return crudMenuRepository.save(menu);
    }

    @Override
    public boolean delete(int id) {
        return crudMenuRepository.delete(id)!=0;
    }

    @Override
    public Menu get(int id) {
        return crudMenuRepository.findOne(id);
    }
}
