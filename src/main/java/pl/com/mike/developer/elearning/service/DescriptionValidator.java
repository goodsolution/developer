package pl.com.mike.developer.elearning.service;

public class DescriptionValidator implements Validator {
    @Override
    public Result validate(String text) {
        return new Result(true, Reason.OK);
    }
}
