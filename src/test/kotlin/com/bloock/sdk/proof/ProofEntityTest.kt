package com.bloock.sdk.proof

import com.bloock.sdk.anchor.entity.Anchor
import com.bloock.sdk.proof.entity.Proof
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProofEntityTest {
    @Test
    fun test_is_valid_okay() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            networks = emptyList(),
            depth = "0004000600060005",
            bitmap = "bfdf7000",
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            ),
        )

        assertTrue(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_minimalist() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = emptyList(),
            depth = "0004",
            bitmap = "bf",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertTrue(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_leaves_not_hex() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aeeg"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            depth = "0004000600060005",
            bitmap = "bfdf7000",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertFalse(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_nodes_not_hex() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aeea"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeag",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            depth = "0004000600060005",
            bitmap = "bfdf7000",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertFalse(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_bitmap_too_short() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee",
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee",
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            depth = "0004000600060005000600060005000600060005",
            bitmap = "bf",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertFalse(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_depth_too_short() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            depth = "000400060006000",
            bitmap = "bfdf7000",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertFalse(Proof.isValid(proof))
    }

    @Test
    fun test_is_valid_depth_too_long() {
        val proof = Proof(
            leaves = listOf("02aae7e86eb50f61a62083a320475d9d60cbd52749dbf08fa942b1b97f50aee5"),
            nodes = listOf(
                "bb6986853646d083929d1d92638f3d4741a3b7149bd2b63c6bfedd32e3c684d3",
                "0616067c793ac533815ae2d48d785d339e0330ce5bb5345b5e6217dd9d1dbeab",
                "68b8f6b25cc700e64ed3e3d33f2f246e24801f93d29786589fbbab3b11f5bcee"
            ),
            depth = "0004000600060",
            bitmap = "bfdf7000",
            networks = emptyList(),
            anchor = Anchor(
                id = 0,
                blockRoots = emptyList(),
                networks = emptyList(),
                root = "",
                status = "Pending"
            )
        )

        assertFalse(Proof.isValid(proof))
    }
}