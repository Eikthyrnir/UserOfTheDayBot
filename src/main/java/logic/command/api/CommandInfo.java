package logic.command.api;

/**
 *  class for CommandFactory
 *  represents name of command and it's argument
 */
class CommandInfo {

    String name;
    String argument;

    CommandInfo(String name, String arg) {
        this.name = name;
        this.argument = arg;
    }

}
