package com.cloudera.poverty.controlller.bi;

import com.cloudera.poverty.common.result.Lay;
import com.cloudera.poverty.common.settings.TableauSettings;
import com.cloudera.poverty.common.utils.TableauUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/bi/embed")
public class TableauController {

    @Autowired
    TableauSettings tableauSettings;

    @GetMapping("/tableau")
    public Lay getEmbedTableauBIUrl() {

        String ticket = "", tickUrl = "", server = this.tableauSettings.getServer(), username = this.tableauSettings.getUsername();
        try {
            ticket = TableauUtils.getTrustedTicket(server, username);
            if (!ticket.equals("-1")) {
                tickUrl = String.format("%s/trusted/%s/t/development/views/1/sheet1", server, ticket);
            }
        } catch (ServletException e) {
            return Lay.error();
        }
        return Lay.ok().data(tickUrl);
    }
}
