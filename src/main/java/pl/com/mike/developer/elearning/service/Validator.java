package pl.com.mike.developer.elearning.service;

public interface Validator {
    Result validate(String text);
    // Todo Result
    //Todo boolean in Result - always true/false, secend Enum - give response why - Reason.OK .. etc., uknow reason - Reason.UNKNOWN
}
