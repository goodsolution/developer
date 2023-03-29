package pl.com.goodsolution.adviser.api.courseplatform;

import java.util.List;

public class ModulesRequestGetResponse {
    private List<ModuleGetResponse> modules;

    public ModulesRequestGetResponse(List<ModuleGetResponse> modules) {
        this.modules = modules;
    }

    public List<ModuleGetResponse> getModules() {
        return modules;
    }
}
