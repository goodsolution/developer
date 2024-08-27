package pl.com.mike.developer.elearning.service;

public class TaskDefinitionValidator implements Validator {

    private static final String PATTERN = "^#title.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\n#title.en\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\r?\\n#description.pl\\(\\)\\{\\s*\".*?\"\\s*\\};\\r?\\n#description.en\\(\\)\\{\\s*\".*?\"\\s*\\};";

    @Override
    public Result validate(String text) {
        //TODO split z klasy string
        //TODO for
        //TODO validacja - każdej linii
        //if false na linii - return
        return null;
    }

    //TODO wszystko co jest w polu - każda rzecz - bla bla bla


//    @Override
//    public Boolean validate(String text) {
//        if(text == null || text.isEmpty()) {
//            return false;
//        }
//        Pattern pattern = Pattern.compile(PATTERN);
//        Matcher matcher = pattern.matcher(text);
//        return matcher.matches();
//    }

    //TODO rozbij tekst na linie, dla każdej linii uruchom validator
    //TODO SPLI na całym tekście # - ; i pętla po każdym i na każdej linii validacja i to jest w metodzie validacja
    //TODO TaskDefinitionValidator - zwraca Result impl Validator
    //TODO CommonvALIDATOR


}
