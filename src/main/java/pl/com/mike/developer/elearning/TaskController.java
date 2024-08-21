package pl.com.mike.developer.elearning;

import org.springframework.web.bind.annotation.*;
import pl.com.mike.developer.elearning.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @GetMapping("/{language}/parse")
//    public TaskDto parseTask(@PathVariable String language, @RequestParam String commandText) {
//        TaskDto taskDto = taskService.parseTask(commandText);
//
//        if ("pl".equalsIgnoreCase(language)) {
//            return new TaskDto(taskDto.getTitlePl(), null, taskDto.getDescriptionPl(), null);
//        } else if ("en".equalsIgnoreCase(language)) {
//            return new TaskDto(null, taskDto.getTitleEn(), null, taskDto.getDescriptionEn());
//        } else {
//            throw new IllegalArgumentException("Invalid language. Use 'pl' or 'en'.");
//        }
//    }

}