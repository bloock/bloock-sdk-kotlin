package com.bloock.sdk.proof.service

import com.bloock.sdk.anchor.entity.Anchor
import com.bloock.sdk.proof.entity.Proof
import com.bloock.sdk.proof.repository.ProofRepository
import com.bloock.sdk.record.entity.Record
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ProofServiceImplTest {

    @MockK
    private lateinit var proofRepository: ProofRepository

    @InjectMockKs
    private lateinit var proofService: ProofServiceImpl


    @Test
    fun test_retrieve_proof_okay_from_json() {
        val json = """{"hello":"world"}"""
        val record = Record.Companion.fromJSON(json)
        val records = listOf(record)
        val expectedProof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            depth = "depth",
            bitmap = "bitmap",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
            networks = emptyList()
        )

        every {
            runBlocking {
                proofRepository.retrieveProof(records)
            }
        } returns expectedProof

        runBlocking {
            var proof = proofService.retrieveProof(records)
            assertEquals(expectedProof, proof)
        }
    }

    @Test
    fun test_retrieve_proof_if_not_document_not_set_proof() {
        val record = Record.fromString("mi-first-record")
        val records = listOf(record)

        val expectedProof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            depth = "depth",
            bitmap = "bitmap",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
            networks = emptyList()
        )

        every {
            runBlocking {
                proofRepository.retrieveProof(records)
            }
        } returns expectedProof

        runBlocking {
            var proof = proofService.retrieveProof(records)
            assertEquals(expectedProof, proof)
        }
    }

    @Test
    fun test_retrieve_proof_if_already_set_return_same_proof() {
        val json = """{"hello":"world"}"""
        val record = Record.Companion.fromJSON(json)
        val expectedProof = Proof(
            leaves = listOf("leave1"),
            nodes = listOf("node1"),
            depth = "depth",
            bitmap = "bitmap",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
            networks = emptyList()
        )
        record.setProof(expectedProof)
        val records = listOf(record)

        every {
            runBlocking {
                proofRepository.retrieveProof(records)
            }
        } returns expectedProof

        runBlocking {
            var proof = proofService.retrieveProof(records)
            assertEquals(expectedProof, proof)
        }
    }
}