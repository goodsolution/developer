package pl.com.goodsolution.adviser.api.courseplatform;

public class ModulePostRequest {
    private Long courseId;
    private String name;

    public ModulePostRequest() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }
}
