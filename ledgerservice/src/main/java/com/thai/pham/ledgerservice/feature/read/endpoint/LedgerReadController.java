package com.thai.pham.ledgerservice.feature.read.endpoint;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.thai.pham.ledgerservice.feature.read.data.request.LedgerReadRequest;

@RestController
@RequestMapping("api/v1/ledgers/")
public class LedgerReadController {
    private final LedgerReadService ledgerReadService;

    @Autowired
    public LedgerReadController(LedgerReadService ledgerReadService) {
        this.ledgerReadService = ledgerReadService;
    }

    @GetMapping
    public ResponseEntity<LedgerPageResult> findLedgerList(@Valid @ParameterObject LedgerReadRequest request) {
        return ResponseEntity.ok(ledgerReadService.findLedgerPage(request));
    }

    @GetMapping("/reference/{ref_id}")
    public ResponseEntity<List<ReaderItemResult>> findLedgerFromRef(@PathVariable("ref_id") String refId) {
        return ResponseEntity.ok(ledgerReadService.findLedgerByRefId(refId));
    }
}