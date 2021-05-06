package com.enchainte.sdk.anchor.service

import com.enchainte.sdk.anchor.entity.Anchor
import com.enchainte.sdk.anchor.entity.exception.WaitAnchorTimeoutException
import com.enchainte.sdk.anchor.repository.AnchorRepository
import com.enchainte.sdk.config.service.ConfigService
import com.enchainte.sdk.shared.Utils
import java.util.*

internal class AnchorServiceImpl internal constructor(
    private val anchorRepository: AnchorRepository,
    private val configService: ConfigService
): AnchorService {
    override suspend fun getAnchor(id: Int): Anchor {
        return anchorRepository.getAnchor(id)
    }

    override suspend fun waitAnchor(id: Int, timeout: Int): Anchor {
        var attempts = 0
        var anchor: Anchor?
        val start = Date().time
        var nextTry = start + this.configService.getConfiguration().WAIT_MESSAGE_INTERVAL_DEFAULT
        val timeoutTime = start + timeout

        while (true) {
            try {
                anchor = this.anchorRepository.getAnchor(id)
                if (anchor.status == "Success") {
                    return anchor
                }

                val currentTime = Date().time

                if (currentTime > timeoutTime) {
                    throw WaitAnchorTimeoutException()
                }

                Utils.sleep(1000)
            } catch (t: Throwable) {
                var currentTime = Date().time
                while (currentTime < nextTry && currentTime < timeoutTime) {
                    Utils.sleep(200)
                    currentTime = Date().time
                }
                nextTry += attempts * this.configService.getConfiguration().WAIT_MESSAGE_INTERVAL_FACTOR + this.configService.getConfiguration().WAIT_MESSAGE_INTERVAL_DEFAULT
                ++attempts

                if (currentTime >= timeoutTime) {
                    throw WaitAnchorTimeoutException()
                }
            }
        }
    }
}