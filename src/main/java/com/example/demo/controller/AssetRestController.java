package com.example.demo.controller;

import com.example.demo.dto.AssetRequest;
import com.example.demo.service.ChaincodeService;
import lombok.RequiredArgsConstructor;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetRestController {

    // Inject the chaincode service to invoke the chaincode
    private final ChaincodeService chaincodeService;

    @PostMapping("init")
    public ResponseEntity<String> initLedger() throws Exception {
        chaincodeService.initLedger();
        return ResponseEntity.ok("Successfully initialized the ledger from the chaincode");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create")
    public ResponseEntity<String> createLedger(@RequestBody AssetRequest assetRequest) throws Exception {
        chaincodeService.createAsset(
                assetRequest.color(),
                String.valueOf(assetRequest.size()),
                assetRequest.owner(),
                String.valueOf(assetRequest.appraisedValue())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Asset created successfully");
    }

    @GetMapping("all")
    public ResponseEntity<String> getAllLedger() throws GatewayException {
        return ResponseEntity.ok(chaincodeService.getAllAssets());
    }

    @PutMapping("transfer")
    public ResponseEntity<String> transferAssetById(
            @RequestParam String assetId,
            @RequestParam String owner) throws Exception {
        chaincodeService.transferAssetAsync(assetId, owner);
        return ResponseEntity.ok("Asset ownership transferred successfully");
    }

    @GetMapping("read-by-id/{id}")
    public ResponseEntity<String> readAssetById(@PathVariable String id) throws Exception {
        String asset = chaincodeService.readAssetById(id);
        return ResponseEntity.ok(asset);
    }
}
