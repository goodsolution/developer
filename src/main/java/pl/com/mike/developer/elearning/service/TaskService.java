package pl.com.mike.developer.elearning.service;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.elearning.TaskDto;

@Service
public class TaskService {

    private final TaskParser taskParser;

    public TaskService(TaskParser taskParser) {
        this.taskParser = taskParser;
    }

    public TaskDto parseTask(String commandText) {
        return taskParser.parseContent(commandText);
    }

}
