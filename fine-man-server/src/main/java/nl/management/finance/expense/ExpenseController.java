package nl.management.finance.expense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Transactional
@RestController
@RequestMapping("users/{userId}/expenses")
public class ExpenseController {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseController.class);
    private final ExpenseMapper mapper;
    private final ExpenseService service;

    @Autowired
    public ExpenseController(ExpenseMapper mapper, ExpenseService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<ExpenseDto> getExpenses(@PathVariable("userId") UUID userId) {
        return mapper.toDto(service.getExpenses(userId));
    }

    @GetMapping("/post-auth")
    public void raboRedirect(@PathParam("code") String code) {

        LOG.error(code);
    }
}
