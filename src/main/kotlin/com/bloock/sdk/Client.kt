package com.bloock.sdk

import com.bloock.sdk.anchor.entity.Anchor
import com.bloock.sdk.anchor.service.AnchorService
import com.bloock.sdk.config.entity.Network
import com.bloock.sdk.config.entity.NetworkConfiguration
import com.bloock.sdk.config.service.ConfigService
import com.bloock.sdk.infrastructure.HttpClient
import com.bloock.sdk.record.entity.Record
import com.bloock.sdk.record.entity.RecordReceipt
import com.bloock.sdk.record.service.RecordService
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.service.ProofService
import com.bloock.sdk.shared.DependencyInjection
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.rx3.rxMaybe
import kotlinx.coroutines.rx3.rxSingle

/**
 * Entry-point to the Bloock SDK
 *
 * This SDK offers all the features available in the Bloock Toolset:
 *  * Write records
 *  * Get records proof
 *  * Validate proof
 *  * Get records details
 *
 * @constructor
 * Constructor with API Key that enables accessing to Bloock's functionalities
 *
 * @param apiKey client API Key
 * @param environment (optional) defines the Bloock's environment to use. By default: production
 */
class BloockClient(private val apiKey: String) {

    private var anchorService: AnchorService = DependencyInjection.getAnchorService()
    private var configService: ConfigService = DependencyInjection.getConfigService()
    private var recordService: RecordService = DependencyInjection.getRecordService()
    private var proofService: ProofService = DependencyInjection.getProofService()

    private var httpClient: HttpClient = DependencyInjection.getHttpClient()

    init {
        httpClient.setApiKey(apiKey)
    }

    /**
     * Overrides the API host.
     *
     * @param host The API host to apply
     */
    fun setApiHost(host: String) {
        this.configService.setApiHost(host)
    }

    /**
     * Overrides a blockchain network configuration
     *
     * @param network The network to modify
     * @param config The new configuration for the specified network
     */
    fun setNetworkConfiguration(network: Network, config: NetworkConfiguration) {
        this.configService.setNetworkConfiguration(network, config)
    }

    /**
     * Sends a list of [Record] to Bloock
     *
     * @param records list of [Record] to send
     * @return RxJava [Single] that will return a list of [RecordReceipt]
     * @throws [InvalidRecordException] At least one of the records sent was not well formed.
     * @throws [HttpRequestException] Error returned by Bloock's API.
     */
    fun sendRecords(records: List<Record<Any>>): Single<List<RecordReceipt>> {
        return rxSingle {
            recordService.sendRecords(records)
        }
    }

    /**
     * Retrieves all [RecordReceipt]s for the specified [Anchor]s
     *
     * @param records to fetch
     * @return a [Single] that will return a list of [RecordReceipt]
     * @throws [InvalidRecordException] At least one of the records sent was not well formed.
     * @throws [HttpRequestException] Error returned by Bloock's API.
     */
    fun getRecords(records: List<Record<Any>>): Single<List<RecordReceipt>> {
        return rxSingle {
            recordService.getRecords(records)
        }
    }

    /**
     * Gets an specific anchor id details
     *
     * @param anchor to look for
     * @return a [Single] that will return a [Anchor] object
     * @throws [InvalidArgumentException] Informs that the input is not a number.
     * @throws [HttpRequestException] Error return by Bloock's API.
     */
    fun getAnchor(anchor: Int): Single<Anchor> {
        return rxSingle {
            anchorService.getAnchor(anchor)
        }
    }

    /**
     * Waits until the anchor specified is confirmed in Bloock
     *
     * @param anchor ID to wait for
     * @param timeout time in miliseconds. After exceeding this time returns an exception. Default = 120000
     * @return a [Single] that will return a [Anchor]
     * @throws [InvalidArgumentException] Informs that the input is not a number.
     * @throws [AnchorNotFoundException] The anchor provided could not be found.
     * @throws [WaitAnchorTimeoutException] Returned when the function has exceeded the timeout.
     * @throws [HttpRequestException] Error return by Bloock's API.
     */
    @JvmOverloads
    fun waitAnchor(anchor: Int, timeout: Int = 120000): Single<Anchor> {
        return rxSingle {
            anchorService.waitAnchor(anchor, timeout)
        }
    }

    /**
     * Retrieves an integrity [Proof] for the specified list of [Record]
     *
     * @param records to validate
     * @return a [Maybe] that will return a [Proof]
     * @throws [InvalidRecordException] At least one of the records sent was not well formed.
     * @throws [HttpRequestException] Error returned by Bloock's API.
     */
    fun getProof(records: List<Record<Any>>): Maybe<Proof> {
        return rxMaybe {
            proofService.retrieveProof(records)
        }
    }

    /**
     * Verifies if the specified integrity [Proof] is valid and checks if
     * it's currently included in the blockchain.
     *
     * @param proof to validate
     * @return a [Boolean] that returns true if valid, false if not
     * @throws [Web3Exception] Error connecting to blockchain.
     */
    fun verifyProof(proof: Proof): Record<*> {
        return proofService.verifyProof(proof)
    }

    /**
     * Validates if the root it's currently included in the blockchain.
     * @param Record root root to validate
     * @param Network network blockchain network where the record will be validated
     * @returns Int A number representing the timestamp in milliseconds when the anchor was registered in Blockchain
     * @throws Web3Exception Error connecting to blockchain.
     */
    fun validateRoot(root: Record<*>, network: Network = Network.ETHEREUM_MAINNET): Int {
        return proofService.validateRoot(root,network)
    }

    /**
     * It retrieves a proof for the specified list of [Anchor] using [getProof] and
     * verifies it using [verifyProof].
     *
     * @param records to verify
     * @return a [Single] that will return true if valid, false if not.
     * @throws [InvalidRecordException] At least one of the records sent was not well formed.
     * @throws [HttpRequestException] Error returned by Bloock's API.
     * @throws [Web3Exception] Error connecting to blockchain.
     */
    fun verifyRecords(records: List<Record<*>>, network: Network?): Single<Int> {
        return rxSingle {
            proofService.verifyRecords(records, network)
        }
    }

    companion object {
        const val VERSION: String = "1.3.1"
    }
}
