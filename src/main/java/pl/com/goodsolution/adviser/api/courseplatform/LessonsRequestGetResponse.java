package pl.com.goodsolution.adviser.api.courseplatform;


import java.util.List;

public class LessonsRequestGetResponse {

    private List<LessonGetResponse> lessonsWithoutModule;
    private List<ModuleGetResponse> modules;

    public LessonsRequestGetResponse(List<LessonGetResponse> lessonsWithoutModule, List<ModuleGetResponse> modules) {
        this.lessonsWithoutModule = lessonsWithoutModule;
        this.modules = modules;
    }

    public List<LessonGetResponse> getLessonsWithoutModule() {
        return lessonsWithoutModule;
    }

    public List<ModuleGetResponse> getModules() {
        return modules;
    }
}
