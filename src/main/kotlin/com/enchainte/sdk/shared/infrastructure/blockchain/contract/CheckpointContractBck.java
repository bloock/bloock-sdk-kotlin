package com.enchainte.sdk.shared.infrastructure.blockchain.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class CheckpointContractBck extends Contract {
    public static final String BINARY = "608060405233600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005157600080fd5b506102a6806100616000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063418e0ac01461004657806382e8deef1461008c5780638da5cb5b146100ba575b600080fd5b6100726004803603602081101561005c57600080fd5b8101908080359060200190929190505050610104565b604051808215151515815260200191505060405180910390f35b6100b8600480360360208110156100a257600080fd5b810190808035906020019092919050505061012d565b005b6100c261024b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600080600083815260200190815260200160002060009054906101000a900460ff169050919050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146101f0576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f53656e646572206e6f7420617574686f72697a65642e0000000000000000000081525060200191505060405180910390fd5b600160008083815260200190815260200160002060006101000a81548160ff021916908315150217905550807f3dfae83a0b2f3013f409fd97c7e72574fcb10cd81987893771d8a2707d533d2160405160405180910390a250565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168156fea265627a7a72315820876b024d3c50253418d47a8f1f7a292141b4a1fd3e8b138f50a9a98c62184aa864736f6c63430005110032\n";

    public static final String FUNC_ADDCHECKPOINT = "addCheckpoint";

    public static final String FUNC_GETCHECKPOINT = "getCheckpoint";

    public static final String FUNC_OWNER = "owner";

    public static final Event NEWCHECKPOINT_EVENT = new Event("NewCheckpoint",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected CheckpointContractBck(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CheckpointContractBck(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CheckpointContractBck(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CheckpointContractBck(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewCheckpointEventResponse> getNewCheckpointEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, transactionReceipt);
        ArrayList<NewCheckpointEventResponse> responses = new ArrayList<NewCheckpointEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NewCheckpointEventResponse typedResponse = new NewCheckpointEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._checkpoint = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewCheckpointEventResponse> newCheckpointEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewCheckpointEventResponse>() {
            @Override
            public NewCheckpointEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(NEWCHECKPOINT_EVENT, log);
                NewCheckpointEventResponse typedResponse = new NewCheckpointEventResponse();
                typedResponse.log = log;
                typedResponse._checkpoint = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewCheckpointEventResponse> newCheckpointEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCHECKPOINT_EVENT));
        return newCheckpointEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addCheckpoint(byte[] _checkpoint) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCHECKPOINT,
                Arrays.<Type>asList(new Bytes32(_checkpoint)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> getCheckpoint(byte[] _checkpoint) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCHECKPOINT,
                Arrays.<Type>asList(new Bytes32(_checkpoint)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static CheckpointContractBck load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CheckpointContractBck(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CheckpointContractBck load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CheckpointContractBck(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CheckpointContractBck load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CheckpointContractBck(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CheckpointContractBck load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CheckpointContractBck(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CheckpointContractBck> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CheckpointContractBck.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CheckpointContractBck> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CheckpointContractBck.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CheckpointContractBck> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CheckpointContractBck.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CheckpointContractBck> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CheckpointContractBck.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewCheckpointEventResponse extends BaseEventResponse {
        public byte[] _checkpoint;
    }
}
