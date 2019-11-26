package logic;

public enum MessageProvider {

    INSTANCE;


    public static MessageProvider getInstance() {
        return INSTANCE;
    }



    private String[] FIND_HANDSOME_MESSAGES = {
            "ВНИМАНИЕ \uD83D\uDD25",
            "Ищем красавчика в этом чате",
            "Гадаем на бинарных опционах \uD83D\uDCCA",
            "Анализируем лунный гороскоп \uD83C\uDF16",
            "Лунная призма дай мне силу \uD83D\uDCAB",
            "СЕКТОР ПРИЗ НА БАРАБАНЕ \uD83C\uDFAF"
    };

    private final String HELP_INFO =
            "/run - start 'user of the day'\n" +
            "/reg - registration\n" +
            "/gamers - show player list";

}
