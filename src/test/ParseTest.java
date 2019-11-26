public class ParseTest {

    public static void main(String[] args) {
        parse("    /reg@MyPidorDayJavaTestBot@MyPidorDayJavaTestBotKUKU   argument1 arg 2 arg333      ");
        parse("");
        parse("/reg");
    }



    private static void parse(String message) {
        message = message.trim();
        String[] parts = message.split("\\s",2);
        String commandName = parts[0];

        if (!commandName.startsWith("/")) {
            System.out.println("" + ", " + "");
            return;
        }

        commandName = commandName.replace("@MyPidorDayJavaTestBot", "");

        String arg = "";
        if(parts.length == 2) {
            arg = parts[1].trim();
        }


        System.out.println("name: " + commandName.substring(1));
        System.out.println("arg: " + arg);
    }
}
