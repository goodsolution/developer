package pl.com.mike.developer.elearning.service;

public class CommandValidator implements Validator {

    @Override
    public Result validate(String text) {
        if (text == null || text.isEmpty()) {
            return new Result(false, Reason.UNKNOWN);
        }
        return new Result(true, Reason.OK);
    }
    //TODO cała linia od # do końca -;
    //czy nazwa komendy OK - enum z nazwami komend
}
