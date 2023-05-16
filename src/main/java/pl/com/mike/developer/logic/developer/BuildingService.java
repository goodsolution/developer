package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.BuildingsGetResponse;
import pl.com.mike.developer.domain.developer.BuildingData;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<BuildingData> getBuildingDataById(BuildingSearchFilter filter) {
     List<BuildingData> buildings = new ArrayList<>();
     buildingRepository.findById(filter.getId()).ifPresent(buildings::add);
     return buildings;
    }



}
