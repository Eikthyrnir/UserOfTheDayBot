import data.entity.Chat;
import data.entity.Player;
import data.service.PlayerService;

public class DatabaseTest {

    public static void main(String[] args) {

        try(PlayerService playerService = new PlayerService()){
            for(Player p : playerService.getPlayersFromChat(new Chat(11))) {
                System.out.println(p.getName());
            }

            playerService.addPlayer(1,1,"liz");
        } catch (Exception e) {
            System.err.println("error");
        }

    }

}
