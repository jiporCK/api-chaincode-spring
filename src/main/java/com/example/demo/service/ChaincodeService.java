package com.example.demo.service;

import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.client.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChaincodeService {

    private final Contract contract;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void initLedger() throws EndorseException, SubmitException, CommitStatusException, CommitException {
        log.info("Submitting transaction: InitLedger");
        contract.submitTransaction("InitLedger");
        log.info("InitLedger transaction committed successfully");
    }

    public String getAllAssets() throws GatewayException {
        log.info("Evaluating transaction: GetAllAssets");
        byte[] result = contract.evaluateTransaction("GetAllAssets");
        return new String(result);
    }

    public void createAsset(String color, String size, String owner, String appraisedValue) throws EndorseException, SubmitException, CommitStatusException, CommitException {
        String assetId = generateAssetId();
        log.info("Creating new asset with ID {}", assetId);

        contract.submitTransaction("CreateAsset", assetId, color, size, owner, appraisedValue);
        log.info("CreateAsset transaction committed successfully for asset ID {}", assetId);
    }

    public void transferAssetAsync(String assetId, String newOwner) throws EndorseException, SubmitException, CommitStatusException {
        log.info("Initiating async transfer of asset ID {} to new owner {}", assetId, newOwner);

        var commit = contract.newProposal("TransferAsset")
                .addArguments(assetId, newOwner)
                .build()
                .endorse()
                .submitAsync();

        var result = commit.getResult();
        var oldOwner = new String(result, StandardCharsets.UTF_8);

        log.info("Successfully submitted transaction to transfer ownership from {} to {}", oldOwner, newOwner);
        log.info("Waiting for transaction commit");

        var status = commit.getStatus();
        if (!status.isSuccessful()) {
            throw new RuntimeException("Transaction " + status.getTransactionId() +
                    " failed to commit with status code " + status.getCode());
        }

        log.info("Transaction committed successfully");
    }

    public String readAssetById(String id) throws GatewayException {
        log.info("Reading asset by ID {}", id);
        return prettyJson(contract.evaluateTransaction("ReadAsset", id));
    }

    private String prettyJson(final byte[] json) {
        return prettyJson(new String(json, StandardCharsets.UTF_8));
    }

    private String prettyJson(final String json) {
        var parsedJson = JsonParser.parseString(json);
        return gson.toJson(parsedJson);
    }

    private String generateAssetId() {
        return "asset" + Instant.now().toEpochMilli();
    }
}
