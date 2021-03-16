package com.enchainte.sdk.anchor.service

import com.enchainte.sdk.anchor.entity.Anchor
import com.enchainte.sdk.anchor.entity.exception.AnchorNotFoundException
import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.config.repository.ConfigRepository
import com.enchainte.sdk.message.entity.MessageReceipt
import com.enchainte.sdk.shared.Utils

internal class AnchorServiceImpl internal constructor(
    private val anchorRepository: AnchorRepository,
    private val configRepository: ConfigRepository
): AnchorService {
    override suspend fun getAnchor(anchor_id: Int): Anchor {
        val anchor = anchorRepository.getAnchor(anchor_id) ?: throw AnchorNotFoundException()

        return Anchor(
            anchor.id ?: 0,
            anchor.blockRoots ?: emptyList(),
            anchor.networks ?: emptyList(),
            anchor.root ?: "",
            anchor.status ?: ""
        )
    }

    override suspend fun waitAnchor(anchor_id: Int): Anchor {
        var attempts = 0
        var anchor: Anchor? = null

        do {
            try {
                val response = anchorRepository.getAnchor(anchor_id)
                if (response != null) {
                    val tempAnchor = Anchor(
                        response.id ?: 0,
                        response.blockRoots ?: emptyList(),
                        response.networks ?: emptyList(),
                        response.root ?: "",
                        response.status ?: ""
                    )

                    if (tempAnchor.status == "Success") {
                        anchor = tempAnchor
                        break
                    }
                }
            } catch (e: AnchorNotFoundException) {}

            Utils.sleep(
                configRepository.getConfiguration().WAIT_MESSAGE_INTERVAL_DEFAULT +
                        attempts * configRepository.getConfiguration().WAIT_MESSAGE_INTERVAL_FACTOR
            )

            attempts += 1
        } while (anchor == null)

        if (anchor == null) {
            throw AnchorNotFoundException()
        }

        return anchor
    }
}