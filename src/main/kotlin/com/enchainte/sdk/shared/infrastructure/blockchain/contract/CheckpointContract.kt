package com.enchainte.sdk.shared.infrastructure.blockchain.contract

import org.web3j.protocol.Web3j
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.tx.TransactionManager
import io.reactivex.Flowable
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.crypto.Credentials
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.BaseEventResponse
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import java.util.*

/**
 *
 * Auto generated code.
 *
 * **Do not modify!**
 *
 * Please use the [web3j command line tools](https://docs.web3j.io/command_line.html),
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * [codegen module](https://github.com/web3j/web3j/tree/master/codegen) to update.
 *
 *
 * Generated with web3j version 4.7.0.
 */
class CheckpointContract : Contract {
    private constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, credentials, contractGasProvider
    )

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, contractGasProvider
    )

    fun getNewCheckpointEvents(transactionReceipt: TransactionReceipt?): List<NewCheckpointEventResponse> {
        val valueList = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, transactionReceipt)
        val responses = ArrayList<NewCheckpointEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = NewCheckpointEventResponse()
            typedResponse.log = eventValues.log
            typedResponse.checkpoint = eventValues.indexedValues[0].value as ByteArray
            responses.add(typedResponse)
        }
        return responses
    }

    fun newCheckpointEventFlowable(filter: EthFilter?): Flowable<NewCheckpointEventResponse> {
        return web3j.ethLogFlowable(filter).map { t ->
            val eventValues = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, t)
            val typedResponse = NewCheckpointEventResponse()
            typedResponse.log = t
            typedResponse.checkpoint = eventValues.indexedValues[0].value as ByteArray
            typedResponse
        }
    }

    fun newCheckpointEventFlowable(
        startBlock: DefaultBlockParameter?,
        endBlock: DefaultBlockParameter?
    ): Flowable<NewCheckpointEventResponse> {
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(NEWCHECKPOINT_EVENT))
        return newCheckpointEventFlowable(filter)
    }

    fun addCheckpoint(_checkpoint: ByteArray?): RemoteFunctionCall<TransactionReceipt> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_ADDCHECKPOINT,
            mutableListOf<Type<*>>(Bytes32(_checkpoint)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun getCheckpoint(_checkpoint: ByteArray?): RemoteFunctionCall<Boolean> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_GETCHECKPOINT,
            mutableListOf<Type<*>>(Bytes32(_checkpoint)),
            mutableListOf<TypeReference<*>>(object : TypeReference<Bool>() {})
        )
        return executeRemoteCallSingleValueReturn(function, Boolean::class.javaObjectType)
    }

    fun owner(): RemoteFunctionCall<String> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_OWNER,
            mutableListOf(),
            mutableListOf<TypeReference<*>>(object : TypeReference<Address?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, String::class.java)
    }

    class NewCheckpointEventResponse : BaseEventResponse() {
        lateinit var checkpoint: ByteArray
    }

    companion object {
        const val BINARY =
            "608060405233600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005157600080fd5b506102a6806100616000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063418e0ac01461004657806382e8deef1461008c5780638da5cb5b146100ba575b600080fd5b6100726004803603602081101561005c57600080fd5b8101908080359060200190929190505050610104565b604051808215151515815260200191505060405180910390f35b6100b8600480360360208110156100a257600080fd5b810190808035906020019092919050505061012d565b005b6100c261024b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600080600083815260200190815260200160002060009054906101000a900460ff169050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146101f0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f53656e646572206e6f7420617574686f72697a65642e0000000000000000000081525060200191505060405180910390fd5b600160008083815260200190815260200160002060006101000a81548160ff021916908315150217905550807f3dfae83a0b2f3013f409fd97c7e72574fcb10cd81987893771d8a2707d533d2160405160405180910390a250565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168156fea265627a7a72315820876b024d3c50253418d47a8f1f7a292141b4a1fd3e8b138f50a9a98c62184aa864736f6c63430005110032\n"
        const val FUNC_ADDCHECKPOINT = "addCheckpoint"
        const val FUNC_GETCHECKPOINT = "getCheckpoint"
        const val FUNC_OWNER = "owner"
        val NEWCHECKPOINT_EVENT = Event("NewCheckpoint",
            mutableListOf<TypeReference<*>>(object : TypeReference<Bytes32?>(true) {})
        )

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): CheckpointContract {
            return CheckpointContract(contractAddress, web3j, credentials, contractGasProvider)
        }

        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): CheckpointContract {
            return CheckpointContract(contractAddress, web3j, transactionManager, contractGasProvider)
        }

        fun deploy(
            web3j: Web3j?,
            credentials: Credentials?,
            contractGasProvider: ContractGasProvider?
        ): RemoteCall<CheckpointContract> {
            return deployRemoteCall(CheckpointContract::class.java, web3j, credentials, contractGasProvider, BINARY, "")
        }

        fun deploy(
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            contractGasProvider: ContractGasProvider?
        ): RemoteCall<CheckpointContract> {
            return deployRemoteCall(
                CheckpointContract::class.java,
                web3j,
                transactionManager,
                contractGasProvider,
                BINARY,
                ""
            )
        }
    }
}