package com.enchainte.sdk.infrastructure.blockchain.contract

import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Bytes32
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.BaseEventResponse
import org.web3j.protocol.core.methods.response.Log
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger
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
internal class CheckpointContract : Contract {
    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        credentials: Credentials?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit
    ) {
    }

    @Deprecated("")
    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        gasPrice: BigInteger?,
        gasLimit: BigInteger?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit
    ) {
    }

    protected constructor(
        contractAddress: String?,
        web3j: Web3j?,
        transactionManager: TransactionManager?,
        contractGasProvider: ContractGasProvider?
    ) : super(
        BINARY, contractAddress, web3j, transactionManager, contractGasProvider
    ) {
    }

    fun getNewCheckpointEvents(transactionReceipt: TransactionReceipt?): List<NewCheckpointEventResponse> {
        val valueList = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, transactionReceipt)
        val responses = ArrayList<NewCheckpointEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = NewCheckpointEventResponse()
            typedResponse.log = eventValues.log
            typedResponse._checkpoint = eventValues.indexedValues[0].value as ByteArray
            responses.add(typedResponse)
        }
        return responses
    }

    fun newCheckpointEventFlowable(filter: EthFilter?): Flowable<NewCheckpointEventResponse> {
        return web3j.ethLogFlowable(filter).map(object : Function<Log?, NewCheckpointEventResponse> {
            override fun apply(t: Log): NewCheckpointEventResponse? {
                val eventValues = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, t)
                val typedResponse = NewCheckpointEventResponse()
                typedResponse.log = t
                typedResponse._checkpoint = eventValues.indexedValues[0].value as ByteArray
                return typedResponse
            }
        })
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
            Arrays.asList<Type<*>>(Bytes32(_checkpoint)), emptyList()
        )
        return executeRemoteCallTransaction(function)
    }

    fun getCheckpoint(_checkpoint: ByteArray?): RemoteFunctionCall<Boolean> {
        val function = org.web3j.abi.datatypes.Function(
            FUNC_GETCHECKPOINT,
            Arrays.asList<Type<*>>(Bytes32(_checkpoint)),
            Arrays.asList<TypeReference<*>>(object : TypeReference<Bool?>() {})
        )
        return executeRemoteCallSingleValueReturn(function, Boolean::class.javaObjectType)
    }

    class NewCheckpointEventResponse : BaseEventResponse() {
        lateinit var _checkpoint: ByteArray
    }

    companion object {
        const val BINARY =
            "608060405234801561001057600080fd5b5061001c60003361004b565b6100467f2b8f168f361ac1393a163ed4adfa899a87be7b7c71645167bdaddd822ae453c83361004b565b610150565b6100558282610059565b5050565b60008281526020818152604090912061007b9183906104cb6100cc821b17901c565b15610055576100886100ea565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b60006100e1836001600160a01b0384166100ee565b90505b92915050565b3390565b60006100fa8383610138565b610130575081546001818101845560008481526020808220909301849055845484825282860190935260409020919091556100e4565b5060006100e4565b60009081526001919091016020526040902054151590565b61086d8061015f6000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80639010d07c116100715780639010d07c1461018557806391d14854146101c45780639beaab7b146101f0578063a217fddf146101f8578063ca15c87314610200578063d547741f1461021d576100a9565b8063248a9ca3146100ae5780632f2ff15d146100dd57806336568abe1461010b578063418e0ac01461013757806382e8deef14610168575b600080fd5b6100cb600480360360208110156100c457600080fd5b5035610249565b60408051918252519081900360200190f35b610109600480360360408110156100f357600080fd5b50803590602001356001600160a01b031661025e565b005b6101096004803603604081101561012157600080fd5b50803590602001356001600160a01b03166102ca565b6101546004803603602081101561014d57600080fd5b503561032b565b604080519115158252519081900360200190f35b6101096004803603602081101561017e57600080fd5b5035610340565b6101a86004803603604081101561019b57600080fd5b50803590602001356103f9565b604080516001600160a01b039092168252519081900360200190f35b610154600480360360408110156101da57600080fd5b50803590602001356001600160a01b031661041a565b6100cb610432565b6100cb610456565b6100cb6004803603602081101561021657600080fd5b503561045b565b6101096004803603604081101561023357600080fd5b50803590602001356001600160a01b0316610472565b60009081526020819052604090206002015490565b6000828152602081905260409020600201546102819061027c6104e0565b61041a565b6102bc5760405162461bcd60e51b815260040180806020018281038252602f8152602001806107aa602f913960400191505060405180910390fd5b6102c682826104e4565b5050565b6102d26104e0565b6001600160a01b0316816001600160a01b0316146103215760405162461bcd60e51b815260040180806020018281038252602f815260200180610809602f913960400191505060405180910390fd5b6102c6828261054d565b60009081526001602052604090205460ff1690565b61036a7f2b8f168f361ac1393a163ed4adfa899a87be7b7c71645167bdaddd822ae453c83361041a565b6103b3576040805162461bcd60e51b815260206004820152601560248201527414d95b99195c881b9bdd08185d5d1a1bdc9a5e9959605a1b604482015290519081900360640190fd5b6000818152600160208190526040808320805460ff19169092179091555182917f3dfae83a0b2f3013f409fd97c7e72574fcb10cd81987893771d8a2707d533d2191a250565b600082815260208190526040812061041190836105b6565b90505b92915050565b600082815260208190526040812061041190836105c2565b7f2b8f168f361ac1393a163ed4adfa899a87be7b7c71645167bdaddd822ae453c881565b600081565b6000818152602081905260408120610414906105d7565b6000828152602081905260409020600201546104909061027c6104e0565b6103215760405162461bcd60e51b81526004018080602001828103825260308152602001806107d96030913960400191505060405180910390fd5b6000610411836001600160a01b0384166105e2565b3390565b60008281526020819052604090206104fc90826104cb565b156102c6576105096104e0565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b6000828152602081905260409020610565908261062c565b156102c6576105726104e0565b6001600160a01b0316816001600160a01b0316837ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b60405160405180910390a45050565b60006104118383610641565b6000610411836001600160a01b0384166106a5565b6000610414826106bd565b60006105ee83836106a5565b61062457508154600181810184556000848152602080822090930184905584548482528286019093526040902091909155610414565b506000610414565b6000610411836001600160a01b0384166106c1565b815460009082106106835760405162461bcd60e51b81526004018080602001828103825260228152602001806107886022913960400191505060405180910390fd5b82600001828154811061069257fe5b9060005260206000200154905092915050565b60009081526001919091016020526040902054151590565b5490565b6000818152600183016020526040812054801561077d57835460001980830191908101906000908790839081106106f457fe5b906000526020600020015490508087600001848154811061071157fe5b60009182526020808320909101929092558281526001898101909252604090209084019055865487908061074157fe5b60019003818190600052602060002001600090559055866001016000878152602001908152602001600020600090556001945050505050610414565b600091505061041456fe456e756d657261626c655365743a20696e646578206f7574206f6620626f756e6473416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e2061646d696e20746f206772616e74416363657373436f6e74726f6c3a2073656e646572206d75737420626520616e2061646d696e20746f207265766f6b65416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636520726f6c657320666f722073656c66a26469706673582212200dd3e0ea3b2c5736249b5dda8574f778a4d7c86a13d3d6a90585eae1c73ed5a964736f6c63430007060033"
        const val FUNC_ADDCHECKPOINT = "addCheckpoint"
        const val FUNC_GETCHECKPOINT = "getCheckpoint"
        val NEWCHECKPOINT_EVENT = Event(
            "NewCheckpoint",
            Arrays.asList<TypeReference<*>>(object : TypeReference<Bytes32?>(true) {})
        )

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): CheckpointContract {
            return CheckpointContract(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        @Deprecated("")
        fun load(
            contractAddress: String?,
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): CheckpointContract {
            return CheckpointContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
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

        @Deprecated("")
        fun deploy(
            web3j: Web3j?,
            credentials: Credentials?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): RemoteCall<CheckpointContract> {
            return deployRemoteCall(CheckpointContract::class.java, web3j, credentials, gasPrice, gasLimit, BINARY, "")
        }

        @Deprecated("")
        fun deploy(
            web3j: Web3j?,
            transactionManager: TransactionManager?,
            gasPrice: BigInteger?,
            gasLimit: BigInteger?
        ): RemoteCall<CheckpointContract> {
            return deployRemoteCall(
                CheckpointContract::class.java,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                BINARY,
                ""
            )
        }
    }
}