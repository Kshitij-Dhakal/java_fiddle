package fiddle;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Log4j2
public class LoggerFiddle {
  public static void main(String[] args) {

    log.info(
        "Rule text didn't match :\nExpected : {}\nActual   : {}",
        "Kshitij Dhakal",
        "Kshitij Dhakal");
  }
}
