package nl.management.finance.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/users/{userId}/bank-accounts/{resourceId}/transactions")
public class TransactionController {
    private final TransactionService service;
    private final TransactionMapper mapper;

    @Autowired
    public TransactionController(TransactionService service, TransactionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TransactionDto> getTransactions(@PathVariable("resourceId") String resourceId, @PathParam("size") int size,
                                                @PathParam("page") int page) {
        return mapper.toDto(service.getTransactions(resourceId, size, page));
    }
}
