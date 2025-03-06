package fiddle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class PatternFiddle {
  public static void main(String[] args) {
    var str =
        "<td valign=\"top\" align=\"left\" width=\"100%\" style=\"border-collapse:collapse\">\n"
            + "\n"
            + "    <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#136AE8\"\n"
            + "        style=\"border-spacing:0;border-collapse:separate;font-family:'Open Sans',Helvetica,sans-serif;font-size:18px;border-radius:5px;text-align:center;text-decoration:none;vertical-align:middle;width:100%;margin:0;background:#ffffff\"\n"
            + "        width=\"100%\">\n"
            + "        <tbody>\n"
            + "            <tr>\n"
            + "                <td style=\"border-collapse:collapse;border-radius:5px;vertical-align:top;border-color:#136ae8;border-style:solid;border-width:10px 13px;background:#136ae8;color:#ffffff;text-align:center\"\n"
            + "                    valign=\"top\">\n"
            + "                    <a href=\"https://efdevhub.info/transactions?other=Triggered%20an%20Alert&amp;utm_source=budgeting-transaction&amp;utm_action=view_details_now&amp;utm_medium=email&amp;utm_campaign=basic&amp;utm_position=main&amp;groupId=500&amp;utm_commid=25126069684\"\n"
            + "                        style=\"color:#ffffff;display:block;text-decoration:none;font-family:'Open Sans',Helvetica,sans-serif\"\n"
            + "                        target=\"_blank\"\n"
            + "                        data-saferedirecturl=\"https://www.google.com/url?q=https://efdevhub.info/transactions?other%3DTriggered%2520an%2520Alert%26utm_source%3Dbudgeting-transaction%26utm_action%3Dview_details_now%26utm_medium%3Demail%26utm_campaign%3Dbasic%26utm_position%3Dmain%26groupId%3D500%26utm_commid%3D25126069684&amp;source=gmail&amp;ust=1732993683022000&amp;usg=AOvVaw1nAzYu1UUFnKzB5GlN206I\">\n"
            + "                        View Details Now\n"
            + "                    </a>\n"
            + "                </td>\n"
            + "            </tr>\n"
            + "        </tbody>\n"
            + "    </table>\n"
            + "</td>";
    str = StringUtils.normalizeSpace(str);
    Pattern compile = Pattern.compile("a href=\"([^\"]*)\"[^>]*>View Details");
    Matcher matcher = compile.matcher(str);
    while (matcher.matches()) {
      System.out.println(matcher.group());
    }
  }
}
