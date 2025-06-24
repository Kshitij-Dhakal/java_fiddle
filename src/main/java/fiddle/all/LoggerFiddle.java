package fiddle.all;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoggerFiddle {
  public static void main(String[] args) {

    log.info(
        "Rule text didn't match :\nExpected : {}\nActual   : {}",
        "Kshitij Dhakal",
        "Kshitij Dhakal");
  }
}
