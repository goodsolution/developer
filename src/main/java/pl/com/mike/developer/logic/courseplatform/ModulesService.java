package pl.com.mike.developer.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.courseplatform.CourseData;
import pl.com.mike.developer.domain.courseplatform.CoursesFilter;
import pl.com.mike.developer.domain.courseplatform.ModuleData;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ModulesService {

    private ModulesJdbcRepository modulesJdbcRepository;
    private final static String DIRECTION_UP = "UP";
    private final static String DIRECTION_DOWN = "DOWN";


    public ModulesService(ModulesJdbcRepository modulesJdbcRepository) {
        this.modulesJdbcRepository = modulesJdbcRepository;
    }

    public ModuleData get(Long id)  {
        return modulesJdbcRepository.get(id);
    }

    public List<ModuleData> find(ModulesFilter filter) {
        return modulesJdbcRepository.find(filter);
    }


    public void update(ModuleData data) {
        validate(data);
        modulesJdbcRepository.update(data);
    }

    public void updateFromPanelAdmin(ModuleData data) {
        ModuleData oldData = get(data.getId());
        String name = data.getName();
        String visibilityStatus = data.getVisibilityStatus();

        ModuleData module = new ModuleData(oldData.getId(), oldData.getCourse(), name, oldData.getOrderNumber(), oldData.getDeleteDatetime(), visibilityStatus);
        update(module);
    }

    public void changeOrder(Long id, String direction) {

        ModuleData module = get(id);
        ModuleData moduleToReplace;

        if(direction.equals(DIRECTION_UP)) {
            moduleToReplace = getFirstAbove(module);
        } else if (direction.equals(DIRECTION_DOWN)) {
            moduleToReplace = getFirstBelow(module);
        } else {
            throw new IllegalArgumentException("Incorrect direction");
        }

        Long orderNumber = module.getOrderNumber();
        update(new ModuleData(module, moduleToReplace.getOrderNumber()));
        update(new ModuleData(moduleToReplace, orderNumber));

    }

    public void delete(Long id) {
        ModuleData module = get(id);
        update(new ModuleData(module, LocalDateTime.now()));
    }

    private ModuleData getFirstAbove(ModuleData module) {
        return modulesJdbcRepository.getFirstAbove(module);
    }

    private ModuleData getFirstBelow(ModuleData module) {
        return modulesJdbcRepository.getFirstBelow(module);
    }


    private void validate(ModuleData data) {
        validateName(data.getName());
        validateVisibilityStatus(data.getVisibilityStatus());
    }

    private void validateVisibilityStatus(String visibilityStatus) {
        if(!visibilityStatus.equals(ModuleVisibilityStatus.INVISIBLE.getValue()) && !visibilityStatus.equals(ModuleVisibilityStatus.VISIBLE.getValue())) {
            throw new IllegalArgumentException("Incorrect visibility status");
        }
    }

    private void validateName(String name) {
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("Name can't be empty");
        } else if(name.length() > 500) {
            throw new IllegalArgumentException("Name too long, max 500 characters");
        }
    }


    private Long prepareOrderNumber() {
        Long maxOrderNumber = modulesJdbcRepository.getMaxOrderNumber();
        if(maxOrderNumber == null) {
            return  1L;
        } else {
            return maxOrderNumber + 1L;
        }
    }
}
